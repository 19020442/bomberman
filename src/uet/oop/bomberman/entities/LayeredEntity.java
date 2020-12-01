package uet.oop.bomberman.entities;

import uet.oop.bomberman.entities.tile.Brick;
import uet.oop.bomberman.graphics.Screen;

import java.util.LinkedList;

public class LayeredEntity extends Entity {
    public LinkedList<Entity> _entities = new LinkedList<>();

    public LayeredEntity(int x, int y, Entity... entities) {
        _x = x;
        _y = y;
        for (int i = 0; i < entities.length; i++) {
            _entities.add(entities[i]);
            if (i > 1) {
                if (entities[i] instanceof Brick) {
                    ((Brick) entities[i]).addBelowSprite(entities[i - 1].getSprite());
                }
            }
        }
    }

    @Override
    public void update() {
        clearRemoved();
        getTopEntity().update();
    }

    @Override
    public void render(Screen screen) {
        getTopEntity().render(screen);
    }

    @Override
    public boolean collide(Entity entity) {
        return getTopEntity().collide(entity);
    }

    public Entity getTopEntity() {
        return _entities.getLast();
    }

    public void clearRemoved() {
        Entity topEntity = getTopEntity();
        if (topEntity.isRemoved()) {
            _entities.removeLast();
        }
    }
}
