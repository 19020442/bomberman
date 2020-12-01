package uet.oop.bomberman.entities.bomb;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.AnimatedEntities;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sounds.SoundEffect;

import java.util.Arrays;
import java.util.Iterator;

public class Bomb extends AnimatedEntities {
    int timeToExplode = 120;
    int timerExplode = 20;
    Flame[] _flame;
    Board _board;
    boolean _explode = false;
    public boolean _allowedToPassThru = true;

    public Bomb(int x, int y, Board board) {
        _x = x;
        _y = y;
        _board = board;
        _sprite = Sprite.bomb;
    }

    @Override
    public void update() {
        if (timeToExplode > 0) {
            timeToExplode--;
        } else {
            if (!_explode) explode();
            else updateFlames();
            if (timerExplode > 0) timerExplode--;
            else remove();
        }
        animate();

    }


    @Override
    public void render(Screen screen) {
        if (_explode) {
            _sprite = Sprite.movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2, Sprite.bomb_exploded1, Sprite.bomb_exploded,_animate, 20);
            renderFlame(screen);
        } else {
            _sprite = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, _animate, 60);
        }
        int x = (int) _x * Game.TILES_SIZE;
        int y = (int) _y * Game.TILES_SIZE;
        screen.renderEntity(x, y, this);

    }

    private void renderFlame(Screen screen) {
        for (Flame flame : _flame) {
            flame.render(screen);
        }
    }

    private void updateFlames() {
        Iterator<Flame> iterator = Arrays.stream(_flame).iterator();
        while (iterator.hasNext()) {
            iterator.next().update();
        }
    }


    @Override
    public boolean collide(Entity entity) {
        if (entity instanceof Bomber) {
            double diffX = entity.getX() - getX()*Game.TILES_SIZE;
            double diffY = entity.getY() - getY()*Game.TILES_SIZE;

            if (!(diffX >= -10 && diffX < 16 && diffY >= 1 && diffY <= 28)) _allowedToPassThru = false;

            return _allowedToPassThru;
        }
        if (entity instanceof Flame) {
            timeToExplode = 0;
            return true;
        }

        return false;
    }

    public void explode()  {
        _explode = true;
        Character character = _board.getCharacterAt(_x, _y);
        if (character != null) {
            character.kill();
        }
        _allowedToPassThru = true;
        _flame = new Flame[4];
        for (int x = 0; x < _flame.length; x++) {
            _flame[x] = new Flame((int) _x, (int) _y, x, Game._radius_bomb, _board);
        }
        SoundEffect explosion_sound =new SoundEffect(SoundEffect.EXPLOSION);
        explosion_sound.play();
    }

    public FlameSegment flameAt(int x, int y) {
        if (!_explode) return null;

        for (int i = 0; i < _flame.length; i++) {
            if (_flame[i] == null) return null;
            FlameSegment e = _flame[i].flameSegmentAt(x, y);
            if (e != null) return e;
        }

        return null;
    }
}
