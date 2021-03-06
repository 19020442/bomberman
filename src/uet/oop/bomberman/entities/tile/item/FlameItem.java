package uet.oop.bomberman.entities.tile.item;

import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Enemy;
import uet.oop.bomberman.entities.tile.Tile;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sounds.SoundEffect;

public class FlameItem extends Tile {
    public boolean _owned;

    public FlameItem(int x, int y, Sprite sprite) {
        super(x, y, sprite);
        _owned = false;
    }

    @Override
    public boolean collide(Entity e) {
        if (e instanceof Bomber) {
            SoundEffect item = new SoundEffect(SoundEffect.EARN_ITEM);
            item.play();
            if (!_owned) {
                _owned = true;
                Game.addRadiusBomb(1);
            }
            remove();
            return true;
        }
        if (e instanceof Enemy) {
            return true;
        }
        return false;
    }
}
