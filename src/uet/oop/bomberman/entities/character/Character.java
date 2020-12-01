package uet.oop.bomberman.entities.character;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.AnimatedEntities;
import uet.oop.bomberman.graphics.Screen;

public abstract class Character extends AnimatedEntities {
    protected int _direction = -1;
    protected boolean alive = true;
    protected boolean _moving = false;
    protected  Board _board;
    public int _timeAfter = 40;
    public Character(int x, int y , Board board) {
        _x = x;
        _y = y;
        _board = board;
    }

    @Override
    public abstract void update();



    @Override
    public abstract void render(Screen screen) ;


    protected abstract void caculateMove();

    public abstract void kill();

    protected abstract void afterKill();

    public abstract boolean canMove(double x, double y);

    public abstract void move(double x, double y);


}
