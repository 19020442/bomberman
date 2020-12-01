package uet.oop.bomberman.entities.bomb;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.entities.character.Enemy;
import uet.oop.bomberman.graphics.Screen;

public class Flame extends Entity {
    int _direction;
    int _radius;
    public Board _board;
    public FlameSegment[] _flameSegments = new FlameSegment[0];

    public Flame(int x, int y, int direction, int radius, Board board) {
        _x = x;
        _y = y;
        _direction = direction;
        _radius = radius;
        _board = board;
        createFlameSegment();
    }

    @Override
    public void update() {
        updateFlameSegments();
    }

    @Override
    public void render(Screen screen) {
        for (int x = 0; x < _flameSegments.length; x++) {
            _flameSegments[x].render(screen);
        }
    }

    @Override
    public boolean collide(Entity entity) {
        if (entity instanceof Character) {

            ((Character) entity) .kill();
            return false;
        }
        return true;
    }

    public void createFlameSegment() {
        _flameSegments = new FlameSegment[calculateLengthFlame()];
        boolean _last = false;
        int x = (int) _x;
        int y = (int) _y;
        for (int i = 0; i < _flameSegments.length; i++) {
            _last = i == _flameSegments.length - 1;
            switch (_direction) {
                case 0:
                    y--;
                    break;
                case 1:
                    x++;
                    break;
                case 2:
                    y++;
                    break;
                case 3:
                    x--;
                    break;
            }
            _flameSegments[i] = new FlameSegment(x, y, _direction, _last);
        }
    }

    public int calculateLengthFlame() {
        int radius = 0;
        int x = (int)_x;
        int y = (int)_y;
        while(radius < _radius) {
            if(_direction == 0) y--;
            if(_direction == 1) x++;
            if(_direction == 2) y++;
            if(_direction == 3) x--;

            Entity a = _board.getEntity(x, y, null);

            if(a instanceof Character) ++radius;

            if(!a.collide(this))
                break;

            ++radius;
        }
        return radius;
    }

    public FlameSegment flameSegmentAt(int x, int y) {
        for (FlameSegment flameSegment : _flameSegments) {
            if (flameSegment.getX() == x && flameSegment.getY() == y)
                return flameSegment;
        }
        return null;
    }
    public void updateFlameSegments() {
        for (int x = 0 ; x< _flameSegments.length ; x ++) {
            _flameSegments[x].update();
        }
    }
}
