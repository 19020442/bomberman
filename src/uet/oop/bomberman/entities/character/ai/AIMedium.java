package uet.oop.bomberman.entities.character.ai;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;

import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Enemy;
import uet.oop.bomberman.entities.tile.Grass;

import uet.oop.bomberman.map.loadMap;

import java.util.List;


public class AIMedium extends uet.oop.bomberman.entities.character.ai.AI {
    Board _board;
    Bomber _bomber;
    Enemy _e;
    int follow = 1;
    int keep = 0;
    int save = 0;

    loadMap _map;
    public char[][] _map_enemy;
    Game _game;


    public AIMedium(Bomber bomber, Enemy e, Board board) {
        _bomber = bomber;
        _e = e;
        _board = board;
        _map = new loadMap(null, _board.get_map().get_level());
        _map_enemy = _map.get_map();
    }

    @Override
    public int calculateDirection() {

        _game = _board.getGame();
        if (!_game.isPaused()) {
            _map_enemy[_e.getYTile()][_e.getXTile()] = '2';
            reLoadMap();
            int area_bomber = seeBomber(_e.getYTile(), _e.getXTile());
            for (int x = 0; x < 13; x++) {
                for (int y = 0; y < 31; y++) {
                    if (_map_enemy[x][y] == '@') {
                        _map_enemy[x][y] = ' ';
                    }
                }
            }
            _map_enemy[_e.getYTile()][_e.getXTile()] = '*';
            updateLevel();
            _map_enemy[_e.getYTile()][_e.getXTile()] = '2';
            if (area_bomber >= 500) {


                if (follow == 1) {

                    int a = calculateColDirection(keep);
                    if (a == 0) {
                        keep = 0;
                    }
                    if (a == 2) keep = 2;
                    if (a == -1) {
                        keep = save;
                        a = calculateRowDirection(keep);
                        if ( a==-1 && _e.getYTile() == _bomber.getYTile()) {
                            if (right()) return 1;
                            if (down()) return 2;
                            if (up()) return 0;
                            if (left()) return 3;
                        } else {
                            follow = 2;
                        }
                    }
                    save = a;
                    return a;
                }
                if (follow == 2) {

                    int a = calculateRowDirection(keep);
                    if (a == -1) {
                        keep = save;
                        a = calculateColDirection(keep);
                        if ( a==-1 && _e.getXTile() == _bomber.getXTile() ) {
                            if (right()) return 1;
                            if (down()) return 2;
                            if (up()) return 0;
                            if (left()) return 3;
                        }else {
                            follow = 1;
                        }

                    }
                    if (a == 1) {
                        keep = 1;
                    }
                    if (a == 3) {
                        keep = 3;
                    }
                    save = a;
                    return a;
                }

            } else {
                return random.nextInt(4);
            }


        }
        return -1;
    }

    protected int calculateColDirection(int keep) {

        if (_bomber.getXTile() < _e.getXTile()) {
            if (left() && _e.canMove(-1, 0)) {
                return 3;
            }
            if (keep == 0) {
                if (up() && _e.canMove(0, -1)) {
                    return 0;
                }
                if (down() && _e.canMove(0, 1)) {
                    return 2;
                }
            }
            if (keep == 2) {
                if (down() && _e.canMove(0, 1)) {
                    return 2;
                }
                if (up() && _e.canMove(0, -1)) {
                    return 0;
                }

            }


        }
        if (_bomber.getXTile() > _e.getXTile()) {
            if (right() && _e.canMove(1, 0)) {
                return 1;
            }
            if (keep == 0) {
                if (up() && _e.canMove(0, -1)) {
                    return 0;
                }
                if (down() && _e.canMove(0, 1)) {
                    return 2;
                }
            }
            if (keep == 2) {
                if (down() && _e.canMove(0, 1)) {
                    return 2;
                }
                if (up() && _e.canMove(0, -1)) {
                    return 0;
                }

            }
        }

        return -1;
    }

    protected int calculateRowDirection(int keep) {
        if (_bomber.getYTile() < _e.getYTile()) {
            if (up() && _e.canMove(0, -1)) {
                return 0;
            }
            if (keep == 1) {
                if (right() && _e.canMove(1, 0)) {
                    return 1;
                }
                if (left() && _e.canMove(-1, 0)) {
                    return 3;
                }
            }
            if (keep == 3) {
                if (left() && _e.canMove(-1, 0)) {
                    return 3;
                }
                if (right() && _e.canMove(1, 0)) {
                    return 1;
                }

            }


        }
        if (_bomber.getYTile() > _e.getYTile()) {
            if (down() && _e.canMove(0, 1)) {
                return 2;
            }
            if (keep == 1) {
                if (right() && _e.canMove(1, 0)) {
                    return 1;
                }
                if (left() && _e.canMove(-1, 0)) {
                    return 3;
                }
            }
            if (keep == 3) {
                if (left() && _e.canMove(-1, 0)) {
                    return 3;
                }
                if (right() && _e.canMove(1, 0)) {
                    return 1;
                }

            }

        }

        return -1;
    }

    protected int seeBomber(int i, int j) {
        if (i < 0 || i >= _map.getHeight() || j < 0 || j >= _map.getWidth()) {
            return 0;
        } else {
            if (i == _bomber.getYTile() && j == _bomber.getXTile()) {
                return 500;
            }

            if (_map_enemy[i][j] != ' ' && _map_enemy[i][j] != '2') {
                return 0;
            }

        }
        _map_enemy[i][j] = '@';
        int detect = 1;
        detect += seeBomber(i - 1, j);
        detect += seeBomber(i, j - 1);
        detect += seeBomber(i, j);
        detect += seeBomber(i, j + 1);
        detect += seeBomber(i + 1, j);
        return detect;
    }

    public void updateLevel() {
        _game = _board.getGame();
        if (_game.isPaused()) return;

        for (int x = 0; x < 13; x++) {
            for (int y = 0; y < 31; y++) {

                if (_map_enemy[x][y] == 'p') {
                    _map_enemy[x][y] = ' ';
                }
                if (_map_enemy[x][y] == '*') {
                    if (_e.getYTile() == x && _e.getXTile() == y) continue;
                    if (_board.getEntity(y, x, null).getClass() == LayeredEntity.class) {
                        if (((LayeredEntity) _board.getEntityAt(y, x)).getTopEntity().getClass() == Grass.class) {
                            _map_enemy[x][y] = ' ';
                        }
                    }
                    if (_board.getEntityAt(y, x).getClass() == Grass.class) {
                        _map_enemy[x][y] = ' ';
                    }

                }
                if (_board.getEntity(y, x, null).getClass() == Bomb.class) {
                    _map_enemy[x][y] = '*';
                }
            }
        }

    }


    public boolean right() {
        _map_enemy[_e.getYTile()][_e.getXTile()] = '*';
        int compare = seeBomber(_e.getYTile(), _e.getXTile() + 1);

        return compare >= 500;
    }

    public boolean down() {
        _map_enemy[_e.getYTile()][_e.getXTile()] = '*';
        int compare = seeBomber(_e.getYTile() + 1, _e.getXTile());
        return compare >= 500;
    }

    public boolean left() {
        _map_enemy[_e.getYTile()][_e.getXTile()] = '*';
        int compare = seeBomber( _e.getYTile(), _e.getXTile() - 1);
        return compare >= 500;
    }

    public boolean up() {
        _map_enemy[_e.getYTile()][_e.getXTile()] = '@';
        int compare = seeBomber( _e.getYTile() - 1, _e.getXTile());
        return compare >= 500;
    }

    public void reLoadMap() {
        for (int x = 0; x < 13; x++) {
            for (int y = 0; y < 31; y++) {
                if (_map_enemy[x][y] == '@') {
                    _map_enemy[x][y] = ' ';
                }
            }
        }
    }

}
