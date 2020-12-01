package uet.oop.bomberman.entities.tile;


import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

public class Brick extends Tile {
    int max_animate = 7500;
    int _animate = 0;
    int _timeToDisappear = 20;

    Sprite _belowSprite = Sprite.grass;
    boolean _break = false;

    public Brick(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

    @Override
    public void update() {
        if (_break) {
            if (_timeToDisappear > 0) {
                _timeToDisappear--;
            }
            else
                remove();

            if (_animate < max_animate) {
                _animate++;
            } else _animate = 0;
        }
    }

    @Override
    public void render(Screen screen) {

        int x = (int) _x * Game.TILES_SIZE;
        int y = (int) _y * Game.TILES_SIZE;
        if (_break) {
            _sprite = movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2);
            screen.renderEntityWithBelowSprite(x, y, this, _belowSprite);
        }
        screen.renderEntity(x, y, this);
    }

    public void broke() {
        _break = true;
    }

    public void addBelowSprite(Sprite sprite) {
        _belowSprite = sprite;
    }

    public Sprite movingSprite(Sprite normal, Sprite s1, Sprite s2) {
        int calc = _animate;
        if (calc < 10) {
            return normal;
        }
        if (calc < 20) return s1;
        return s2;
    }
    public boolean collide(Entity entity) {
        if (entity instanceof Flame) {
            broke();
        }
        return false;
    }
}
