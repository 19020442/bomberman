package uet.oop.bomberman;

import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.FlameSegment;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Character;

import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.input.Keyboard;
import uet.oop.bomberman.map.loadMap;
import uet.oop.bomberman.sounds.SoundEffect;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Board  {
    loadMap _map;
    Game _game;
    Screen _screen;
    Keyboard _input;
    public Entity[] _entities;
    public List<Character> _characters = new ArrayList<>();
    public List<Bomb> _bomb = new ArrayList<>();

    public int _time = Game.TIME;
    public int _point = Game.POINT;

    public int _show = -1;

    public Keyboard get_input() {
        return _input;
    }

    public Board(Game game, Screen screen, Keyboard input) {
        _game = game;
        _screen = screen;
        _input = input;
        SoundEffect background  = new SoundEffect(SoundEffect.BACKGROUND);
        background.loop();
        loadLevel(1);

    }
    public void update() {
        if( _game.isPaused() ) return;
        updateBombs();
        updateCharacters();
        updateEntities();
        detectEndgame();
        for (int i = 0; i < _characters.size(); i++) {
            Character character = _characters.get(i);
            if (character.isRemoved()) _characters.remove(i);
        }
    }

    public void render(Screen screen) {
        if( _game.isPaused() ) return;
        int x0 = Screen.xOffSet / Game.TILES_SIZE;
        int x1 = (Screen.xOffSet + _screen.getWidth() + Game.TILES_SIZE) / Game.TILES_SIZE;
        int y0 = Screen.yOffSet / Game.TILES_SIZE;
        int y1 = (Screen.yOffSet + _screen.getHeight()) / Game.TILES_SIZE;
        for (int y = y0; y < y1; y++) {
            for (int x = x0; x < x1; x++) {
                _entities[x + y * _map.get_width()].render(_screen);
            }
        }
        renderBomb(screen);
        renderCharacter(screen);
    }

    public void updateEntities() {
        if( _game.isPaused() ) return;
        for (Entity entity : _entities) {
            entity.update();
        }
    }

    public void updateCharacters() {
        if( _game.isPaused() ) return;
        Iterator<Character> characterIterator = _characters.iterator();
        while (characterIterator.hasNext() && !_game._pause) {
            characterIterator.next().update();
        }
    }

    public void updateBombs() {
        if( _game.isPaused() ) return;
        for (Bomb bomb : _bomb) {
            bomb.update();
        }
    }


    public FlameSegment getFlameSegmentAt(int x, int y) {
        Iterator<Bomb> iterator = _bomb.iterator();
        Bomb bomb;
        while (iterator.hasNext()) {
            bomb = iterator.next();

            FlameSegment flameSegment = bomb.flameAt(x, y);
                if (flameSegment != null) return flameSegment;
        }
        return null;
    }


    public boolean noEnemy() {
        int rest = 0;
        for (Character character : _characters) {
            if (!(character instanceof Bomber)) {
                rest++;
            }
        }
        return rest == 0;
    }


    public void endGame() {
        _show = 2;
        _game._delay = 2;
        _game.pause();

    }

    public void detectEndgame() {
        if (_time <= 0) {
            endGame();
        }
    }

    public void loadLevel(int level) {
        _show = 1;
        _game.pause();
        _game._delay = 2;
        _characters.clear();
        _bomb.clear();
        _map = new loadMap(this, level);
        _entities = new Entity[_map.get_height() * _map.get_width()];
        _map.createEntities();

    }

    public void drawState(Graphics g) {
        switch (_show) {
            case 1:
                _screen.drawChangeLevel(g, _map.get_level());
                break;
            case 2:
                _screen.drawEndGame(g, get_point());
                break;
        }
    }

    public Entity getEntity(double x, double y, Character m) {
        Entity entity;
        entity = getFlameSegmentAt((int) x, (int) y);
        if (entity != null) return entity;

        entity = getCharacterAt(x, y);
        if (entity != null) return entity;

        entity = getBombAt(x, y);
        if (entity != null) return entity;

        entity = getEntityAt(x, y);
        return entity;
    }

    private Entity getBombAt(double x, double y) {
        for (int i = 0; i < _bomb.size(); i++) {
            if (_bomb.get(i).getX() == x && _bomb.get(i).getY() == y) {
                return _bomb.get(i);
            }
        }
        return null;
    }

    public Entity getEntityAt(double x, double y) {
        return _entities[(int) x + (int) y * _map.get_width()];
    }

    public Character getCharacterAt(double x, double y) {
        Iterator iterator = this._characters.iterator();
        Character character;
        do {
            if (!iterator.hasNext()) {
                return null;
            }
            character = (Character) iterator.next();
        } while ((double) character.getXTile() != x || (double) character.getYTile() != y);
        return character;
    }

    public Bomber getBomber() {
        Iterator<Character> iterator = _characters.iterator();
        Character character;
        while (iterator.hasNext()) {
            character = iterator.next();
            if (character instanceof Bomber) {
                return (Bomber) character;
            }
        }
        return null;
    }

    public List<Bomb> get_bomb() {
        return _bomb;
    }

    public int subTime() {
        if (_game.is_pause()) {
            return _time;
        } else return _time--;
    }

    public Game get_game() {
        return _game;
    }

    public int get_time() {
        return _time;
    }

    public int get_point() {
        return _point;
    }

    public void addPoint(int add) {
        this._point += add;
    }

    public void addEntity(int position, Entity entity) {
        _entities[position] = entity;


    }

    public void addCharacter(Character character) {
        _characters.add(character);
    }

    public void renderCharacter(Screen screen) {
        for (Character character : _characters) {
            character.render(screen);
        }
    }
    public void renderEntity(Screen screen) {
        for (Entity entity : _entities) {
            entity.render(screen);
        }
    }

    public void addBomb(Bomb bomb) {
        _bomb.add(bomb);
    }

    public void renderBomb(Screen screen) {
        for (Bomb bomb : _bomb) {
            bomb.render(screen);
        }
    }

    public int get_width() {
        return _map._width;
    }

    public int get_height() {
        return _map._height;
    }

    public loadMap get_map() {
        return _map;
    }

    public void nextLevel() {
        loadLevel(_map.get_level() + 1);
    }

    public int get_show() {
        return _show;
    }

    public void set_show(int i) {
        _show = i;
    }

    public Game getGame() {
        return _game;
    }
}
