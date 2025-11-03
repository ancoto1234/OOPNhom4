package core;

import java.io.Serializable;
import java.util.*;

public class SaveGame implements Serializable {
    public int score;
    public int lives;
    public int currentLevel;
    public boolean isSpaced;

    public List<BrickData> bricks;
    public List<BallData> balls;
    public PaddleData paddle;
    public List<PowerUpData> powerups;

    public static class BrickData implements Serializable {
        public int x, y, width, height, type;
    }

    public static class BallData implements Serializable {
        public int x, y, width, height, speed;
        public double dx, dy;
    }

    public static class PaddleData implements Serializable {
        public int x, y, width, height;
    }

    public static class PowerUpData implements Serializable {
        public int x, y, width, height, type;
    }
}
