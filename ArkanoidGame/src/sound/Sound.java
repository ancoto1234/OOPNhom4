package sound;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class Sound {
    private Clip clip;
    private FloatControl volumeControl;
    private String filePath;

    public Sound(String filePath) {
        this.filePath = filePath;
        // tải âm thanh ngay khi khởi tạo
        loadSound(filePath);
    }

    private void loadSound(String filePath) {
        try {
            File soundFile = new File(filePath);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error loading sound file: " + filePath + " - " + e.getMessage());
        }
    }

    public void play() {
        if (clip != null) {
            clip.stop();
            clip.setFramePosition(0);
            clip.start();
        }
    }

    public void loop() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }
    
    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public void setVolume(float db) {
        if (volumeControl != null) {
            volumeControl.setValue(db);
        }
    }

    public String getFilePath() { return filePath; }
}