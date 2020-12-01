package uet.oop.bomberman.entities.character;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.character.ai.AI;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Enemy extends Character {
    protected double _speed;
    protected Sprite _dead;
    protected int _point;
    protected int _finalAnimation = 30;

    protected AI _ai;

    protected final double MAX_STEPS;
    protected final double rest;
    protected double _steps;
    public Enemy(int x, int y, Board board, Sprite dead, double speed, int point) {
        super(x, y, board);
        _dead = dead;
        _speed = speed;
        _point = point;

        MAX_STEPS = Game.TILES_SIZE / _speed;
        rest = (MAX_STEPS - (int) MAX_STEPS) / MAX_STEPS;
        _steps = MAX_STEPS;
    }

    @Override
    public void update() {
        animate();
        if (!alive) {
            afterKill();
            return;
        }
        caculateMove();


    }

    @Override
    public void render(Screen screen) {

        if (alive) {
            chooseSprite();
        } else {
            if (_timeAfter > 0) {
                _sprite = _dead;
                _animate = 0;
            } else {
                _sprite = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, _animate, 60);
            }
        }
        screen.renderEntity((int) _x, (int) _y - _sprite.SIZE, this);

    }

    @Override
    public void caculateMove() {
        int xa = 0, ya = 0;
        if(_steps <= 0){
            _direction = _ai.calculateDirection();
            _steps = MAX_STEPS;
        }

        if(_direction == 0) ya--;
        if(_direction == 2) ya++;
        if(_direction == 3) xa--;
        if(_direction == 1) xa++;

        if(canMove(xa, ya)) {
            _steps -= 1 + rest;
            move(xa * _speed, ya * _speed);
            _moving = true;
        } else {
            _steps = 0;
            _moving = false;
        }
    }

    @Override
    public void kill() {
        if (!alive) return;
        alive = false;
        _board.addPoint(_point);
    }

    @Override
    protected void afterKill() {
        if (_timeAfter > 0) --_timeAfter;
        if (_timeAfter == 0){
            if(_finalAnimation > 0) --_finalAnimation;
            else {
                remove();

            }
        }
    }

    @Override
    public boolean canMove(double x, double y) {
        double xr = _x, yr = _y - 16;

        if(_direction == 0) {
            yr += _sprite.getSize() -1 ;
            xr += _sprite.getSize() / 2;
        }
        if(_direction == 1) {
            yr += _sprite.getSize() / 2;
            xr += 1;
        }
        if(_direction == 2) {
            xr += _sprite.getSize() / 2;
            yr += 1;
        }
        if(_direction == 3) {
            xr += _sprite.getSize() -1;
            yr += _sprite.getSize() / 2;
        }

        int xx = (int)( xr/16 )+(int)x;
        int yy = (int)(yr/16) +(int)y;

        Entity a = _board.getEntity(xx, yy,this);

        return a.collide(this);
    }

    @Override
    public void move(double x, double y) {
        if (!alive) return;
        _y += y;
        _x += x;
    }

    @Override
    public boolean collide(Entity entity) {
        if (entity instanceof Flame) {
            kill();
            return false;
        }
        if (entity instanceof Bomber) {
            ((Bomber) entity).kill();
            return true;
        }
        return true;
    }

    public abstract void chooseSprite();
}
