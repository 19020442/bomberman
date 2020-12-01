package uet.oop.bomberman.entities.tile;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class Portal extends Tile {
    public Board _board;

    public Portal(int x, int y, Board board, Sprite sprite) {
        super(x, y, sprite);
        _board = board;
    }

    @Override
    public boolean collide(Entity entity) {
        if (entity instanceof Bomber) {
            if (!_board.noEnemy())
                return false;
            if (entity.getXTile() == getX() && entity.getYTile() == getY()) {
                if (_board.noEnemy()) {
                    _board.nextLevel();
                }
            }
            return true;

        }
        return false;
    }


}
