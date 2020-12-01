package uet.oop.bomberman.gui;

import uet.oop.bomberman.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * Swing Frame chứa toàn bộ các component
 */
public class Frame extends JFrame {

    GamePanel _gamepanel;
    private JPanel _containerpane;
    private JPanel _menu;
    private InfoPanel _infopanel;
    private boolean offline = false;
    private boolean exit = false;
    private Game _game;


    public Frame() throws IOException {

        URL a = Frame.class.getResource("/Bomberman-icon.png");
        try {
            BufferedImage image = ImageIO.read(a);
            setIconImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        _containerpane = new JPanel(new BorderLayout());
        _gamepanel = new GamePanel(this);
        _infopanel = new InfoPanel(_gamepanel.getGame());

        _containerpane.add(_infopanel, BorderLayout.PAGE_START);
        _containerpane.add(_gamepanel, BorderLayout.PAGE_END);

        _game = _gamepanel.getGame();

        add(_containerpane);

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        _game.start();
    }

    public void setTime(int time) {
        _infopanel.setTime(time);
    }

    public void setPoints(int points) {
        _infopanel.setPoints(points);
    }



}

