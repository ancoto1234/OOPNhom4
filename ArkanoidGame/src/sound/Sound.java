package sound;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
    private Clip clip;
    private FloatControl volumeControl;
    private String filePath;

    public Sound(String filePath) {}

    public void sound(String filePath) {}
    public void play() {}
    public void loop() {}
    public void stop() {}
    public void setVolume(float value) {}
    public String getFilePath() { return filePath; }
}
