package core;

import java.io.*;

public class HighScoreManager {
    private static final String FILE_PATH = "ArkanoidGame/data/highscore.dat";
    private int highScore;

    public HighScoreManager() {
        highScore = loadHighScore();
    }

    //Đọc điểm cao nhất
    private int loadHighScore() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            return Integer.parseInt(reader.readLine());
        } catch (Exception e) {
            return 0;
        }
    }

    // Ghi điểm cao nhất
    private void saveHighScore(int newHighScore) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write(String.valueOf(newHighScore));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Cập nhật điểm cao nhất
    public void checkAndUpdate(int currentScore) {
        if (currentScore > highScore) {
            highScore = currentScore;
            saveHighScore(highScore);
        }
    }

    public int getHighScore() {
        return highScore;
    }
}
