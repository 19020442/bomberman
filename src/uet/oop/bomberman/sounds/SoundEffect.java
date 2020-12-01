package uet.oop.bomberman.sounds;

import javax.sound.sampled.*;
import java.io.IOException;

public class SoundEffect implements Runnable {
    public static final String BACKGROUND = "bg";
    public static final String INSTALL_BOMB = "install_bomb";
    public static final String EARN_ITEM = "earn_item";
    public static final String EXPLOSION = "explosion";
    public static final String DEAD = "dead";

    private Clip clip;

    public enum Loop {
        noLooping, loop;
    }

    private Loop loop = Loop.noLooping;

    public SoundEffect(String fileName)  {
        String path = "/sounds/" + fileName + ".wav";
        AudioInputStream sound = null;
        try {
            sound = AudioSystem.getAudioInputStream(getClass().getResource(path));
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        try {
            clip.open(sound);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void play() {
        clip.setFramePosition(0);
        clip.start();
    }

    public void stop() {
        clip.stop();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    @Override
    public void run() {
        switch (loop) {
            case loop:
                loop();
                break;
            case noLooping:
                play();
                break;
        }
    }
}
