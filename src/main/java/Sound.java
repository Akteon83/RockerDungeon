package main.java;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {

    private final Clip clip;

    public Sound(File file) {
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audio);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    public void play() {
        clip.start();
    }

    public void stop() {
        clip.stop();
    }
}
