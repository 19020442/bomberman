package uet.oop.bomberman.entities.character;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.input.Keyboard;
import uet.oop.bomberman.sounds.SoundEffect;

import java.util.Iterator;
import java.util.List;

public class Bomber extends Character {
    public Keyboard _input;
    public List<Bomb> _bombs;
    public int _timeBetweenPutBomb = 7500;

    public Bomber(int x, int y, Board board) {
        super(x, y, board);
        _sprite = Sprite.player_right;
        _input = _board.get_input();
        _bombs = _board.get_bomb();

    }

    @Override
    public void update() {
        clearBombs();
        if (!alive) {
            afterKill();
            return;
        }

        if (_timeBetweenPutBomb < 0) _timeBetweenPutBomb = 7500;
        else _timeBetweenPutBomb--;
        caculateMove();
        animate();
        installBomb();
    }


    @Override
    public void render(Screen screen) {
        calculateXOffset();
        if (alive) {
            chooseSprite();
        } else {

            _sprite = Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3, _animate, 60);
        }
        screen.renderEntity((int) _x, (int) _y - _sprite.SIZE, this);
    }
    private void clearBombs() {
        Iterator<Bomb> bombIterator = _bombs.iterator();
        Bomb bomb;
        while (bombIterator.hasNext()) {
            bomb = bombIterator.next();
            if (bomb.isRemoved()) {
                bombIterator.remove();
                Game.addBomb(1);
            }
        }
    }

    private void calculateXOffset() {
        int xScroll = Screen.calculateXOffset(_board, this);
        Screen.setOffset(xScroll, 0);
    }

    public void placeBomb(int x, int y) {
        Bomb bomb = new Bomb(x, y, _board);
        _board.addBomb(bomb);
        SoundEffect install_bomb = new SoundEffect(SoundEffect.INSTALL_BOMB);
        install_bomb.play();

    }

    public void installBomb() {
        if (_input.bomb && Game.get_num_bomb() >= 1 && _timeBetweenPutBomb < 7500) {
            int x = (int) (_x + _sprite.getSize() / 2) / Game.TILES_SIZE;
            int y = (int) ((_y + _sprite.getSize() / 2) - _sprite.getSize()) / Game.TILES_SIZE;
            placeBomb(x, y);
            Game.addBomb(-1);
            _timeBetweenPutBomb = 7510;
        }

    }

    @Override
    public void caculateMove() {
        int xa = 0, ya = 0;
        if (_input.up) ya--;
        if (_input.down) ya++;
        if (_input.left) xa--;
        if (_input.right) xa++;

        if (xa != 0 || ya != 0) {
            move(xa * Game.get_speed_bomber(), ya * Game.get_speed_bomber());
            _moving = true;
        } else _moving = false;
    }

    @Override
    public void kill() {
        if (!alive) return;
        alive = false;
        SoundEffect dead = new SoundEffect(SoundEffect.DEAD);
        dead.play();
    }

    @Override
    public void afterKill() {
        if (_timeAfter > 0) --_timeAfter;
        else {
            _board.endGame();

        }
    }

    @Override
    public boolean canMove(double x, double y) {
        for (int c = 0; c < 4; c ++) {
            double xt = ((_x + x) + c % 2 * 11) / Game.TILES_SIZE;
            double yt = ((_y + y) + c / 2 * 12 - 13) / Game.TILES_SIZE;

            Entity a = _board.getEntity(xt, yt, this);

            if(!a.collide(this))
                return false;
        }

        return true;

    }

    @Override
    public void move(double x, double y) {
        if (canMove(0, y)) _y += y;
        if (canMove(x, 0)) _x += x;

        if (_input.up) _direction = 0;
        if (_input.right) _direction = 1;
        if (_input.down) _direction = 2;
        if (_input.left) _direction = 3;

    }

    @Override
    public boolean collide(Entity entity) {
        if (entity instanceof Flame) {
            kill();
            return false;
        }

        if (entity instanceof Enemy) {
            kill();
            return true;
        }
        return true;
    }

    public void chooseSprite() {

            switch (_direction) {
                case 0:
                    _sprite = Sprite.player_up;
                    if (_moving) {
                        _sprite = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, _animate, 20);
                    }
                    break;
                case 1:
                    _sprite = Sprite.player_right;
                    if (_moving) {
                        _sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, _animate, 20);
                    }
                    break;
                case 2:
                    _sprite = Sprite.player_down;
                    if (_moving) {
                        _sprite = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, _animate, 20);

                    }
                    break;
                case 3:
                    _sprite = Sprite.player_left;
                    if (_moving) {
                        _sprite = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, _animate, 20);
                    }
                    break;
            }
        }

}
