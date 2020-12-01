package uet.oop.bomberman.entities;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public abstract class Entity  {
    protected double _x;
    protected double _y;
    protected Sprite _sprite;
    protected boolean _removed = false;


    public abstract void update();

    public abstract void render(Screen screen);

    public abstract boolean collide(Entity entity);

    public void remove() {
        _removed = true;
    }

    public double getX() {
        return _x;
    }

    public double getY() {
        return _y;
    }

    public int getXTile() {
        return (int) (_x + _sprite.SIZE / 2) / Game.TILES_SIZE;
    }

    public int getYTile() {
        return (int) (_y - _sprite.SIZE / 2) / Game.TILES_SIZE;
    }

    public Sprite getSprite() {
        return _sprite;
    }

    public boolean isRemoved() {
        return _removed;
    }
}
