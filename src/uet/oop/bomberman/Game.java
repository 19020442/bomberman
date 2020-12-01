package uet.oop.bomberman;

import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.gui.Frame;
import uet.oop.bomberman.input.Keyboard;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;

public class Game extends Canvas {
    public static int TILES_SIZE = 16;
    public static int WIDTH = TILES_SIZE * (31 / 2),
                      HEIGHT = 13 * TILES_SIZE;
    public static int SCALE = 3;

    public String title = "BombermanGame";

    public static int TIME = 200;
    public static int POINT = 0;

    public static int _radius_bomb = 1;
    public static int _num_bomb = 1;
    public static double _speed_bomber = 1.0;


    public int _delay = 1;
    public boolean _pause = true;

    private uet.oop.bomberman.gui.Frame _frame;
    private Board _board;
    private Screen _screen;
    private Keyboard _input;

    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

    public boolean _running = false;

    public Game(Frame frame) {
        _frame = frame;
        _frame.setTitle(title);

        _screen = new Screen(WIDTH, HEIGHT);
        _input = new Keyboard();
        _board = new Board(this, _screen, _input);
        addKeyListener(_input);

    }

    public static void addBomb(int i) {
        _num_bomb += i;
    }

    public static void addSpeedBomber(double i) {
        _speed_bomber += i;
    }

    public static void addRadiusBomb(int i) {
        _radius_bomb += i;
    }

    public void renderGame() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        _screen.clear();
        _board.render(_screen);
        System.arraycopy(_screen._pixels, 0, pixels, 0, pixels.length);

        Graphics g = bs.getDrawGraphics();

        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);

        g.dispose();
        bs.show();
    }

    public void renderScreen() throws IOException {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(4);
            return;
        }

        _screen.clear();

        Graphics g = bs.getDrawGraphics();

        _board.drawState(g);

        g.dispose();
        bs.show();

    }

    public void update() {
        _input.update();
        _board.update();
    }

    public void start() throws IOException {
        _running = true;


        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / 60.0; //nanosecond, 60 frames per second
        double delta = 0;
        int frames = 0;
        int updates = 0;
        requestFocus();
        while (_running) {

            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                update();
                updates++;
                delta--;
            }
            if (_pause) {
                if (_delay <= 0) {
                    _board.set_show(-1);
                    _pause = false;
                }
                renderScreen();
            } else {
                renderGame();
            }

            frames++;
            if (System.currentTimeMillis() - timer > 1000) {
                _frame.setTime(_board.subTime());
                _frame.setPoints(_board.get_point());
                timer += 1000;
                _frame.setTitle(updates + " fps");
                updates = 0;
                frames = 0;
                if (_board.get_show() == 1) {
                    --_delay;
                }
            }

        }
    }

    public static int get_num_bomb() {
        return _num_bomb;
    }

    public static double get_speed_bomber() {
        return _speed_bomber;
    }

    public Board get_board() {
        return _board;
    }

    public static int get_radius_bomb() {
        return _radius_bomb;
    }

    public boolean is_pause() {
        return _pause;
    }

    public void pause() {
        _pause = true;
    }

    public boolean isPaused() {
        return _pause;
    }
}
