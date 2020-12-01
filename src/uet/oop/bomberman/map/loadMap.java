package uet.oop.bomberman.map;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.character.Balloon;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Oneal;
import uet.oop.bomberman.entities.tile.*;
import uet.oop.bomberman.entities.tile.item.BombItem;
import uet.oop.bomberman.entities.tile.item.FlameItem;
import uet.oop.bomberman.entities.tile.item.SpeedItem;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class loadMap {
    public int _width;
    public int _height;
    public int _level;
    Board _board;

    private static char[][] _map;

    public loadMap(Board board, int level) {
        _board = board;
        _level = level;
        loadLevel(level);
    }

    public void loadLevel(int level) {

        BufferedReader bufferedReader = null;
        URL a = this.getClass().getResource("/levels/Level" + level + ".txt");
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(a.openStream()));
            String[] firstLine = bufferedReader.readLine().split(" ");
            _level = Integer.parseInt(firstLine[0]);
            _height = Integer.parseInt(firstLine[1]);
            _width = Integer.parseInt(firstLine[2]);

            _map = new char[_height][_width];
            String line;
            for (int i = 0; i < _height; i++) {
                line = bufferedReader.readLine();
                for (int j = 0; j < _width; j++) _map[i][j] = line.charAt(j);
            }
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public void createEntities() {
        for (int y = 0; y < _height; y++) {
            for (int x = 0; x < _width; x++) {
                addEntities(_map[y][x], x, y);
            }
        }

    }

    public void addEntities(char c, int x, int y) {
        int position = x + y * _width;
        switch (c) {
            case '#':
                _board.addEntity(position, new Wall(x, y, Sprite.wall));
                break;
            case 'b':
                _board.addEntity(position,
                        new LayeredEntity(x, y,
                                new Grass(x, y, Sprite.grass),
                                new BombItem(x, y, Sprite.powerup_bombs),
                                new Brick(x, y, Sprite.brick)
                        )
                );
                break;
            case 's':
                _board.addEntity(position,
                        new LayeredEntity(x, y,
                                new Grass(x, y, Sprite.grass),
                                new SpeedItem(x, y, Sprite.powerup_speed),
                                new Brick(x, y, Sprite.brick)
                        )
                );
                break;
            case 'f':
                _board.addEntity(position,
                        new LayeredEntity(x, y,
                                new Grass(x, y, Sprite.grass),
                                new FlameItem(x, y, Sprite.powerup_flames),
                                new Brick(x, y, Sprite.brick)
                        )
                );
                break;
            // thêm Brick
            case '*':
                _board.addEntity(position, new LayeredEntity(x, y,
                        new Grass(x, y, Sprite.grass),
                        new Brick(x, y, Sprite.brick)));
                break;
            // thêm Portal
            case 'x':
                _board.addEntity(position, new LayeredEntity(x, y,
                        new Grass(x, y, Sprite.grass),
                        new Portal(x, y, _board, Sprite.portal),
                        new Brick(x, y, Sprite.brick)));
                break;
            // thêm Grass
            case ' ':
                _board.addEntity(position, new Grass(x, y, Sprite.grass));

                break;
            // thêm Bomber
            case 'p':
                _board.addCharacter(new Bomber(x * Game.TILES_SIZE, y * Game.TILES_SIZE + Game.TILES_SIZE, _board));
                Screen.setOffset(0, 0);

                _board.addEntity(position, new Grass(x, y, Sprite.grass));
                break;
            // thêm Enemy
            case '1':
                _board.addCharacter(new Balloon(x * Game.TILES_SIZE, y * Game.TILES_SIZE + Game.TILES_SIZE, _board));
                _board.addEntity(position, new Grass(x, y, Sprite.grass));
                break;
            case '2':
                _board.addCharacter(new Oneal(x * Game.TILES_SIZE, y * Game.TILES_SIZE + Game.TILES_SIZE, _board));
                _board.addEntity(position, new Grass(x, y, Sprite.grass));
                break;
            default:
                _board.addEntity(position, new Grass(x, y, Sprite.grass));
                break;


        }
    }

    public int get_width() {
        return _width;
    }

    public int get_height() {
        return _height;
    }

    public int get_level() {
        return _level;
    }

    public Board get_board() {
        return _board;
    }

    public char[][] get_map() {
        return _map;
    }

    public int getHeight() {
        return _height;
    }

    public int getWidth() {
        return _width;
    }
}
