

package menu;

import UI.Button;
import core.MenuManager;
import java.awt.*;
import javax.swing.*;

public class EndGamePanel extends JPanel {
    private MenuManager manager;
    private Font arcadeFont;
    private JLabel statusLabel;
    private JLabel scoreLabel;
    private JLabel highScoreLabel;

    public EndGamePanel(MenuManager manager) {
        this.manager = manager;
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);

        try {
            arcadeFont = Font.createFont(Font.TRUETYPE_FONT, new java.io.File("ArkanoidGame/assets/font.ttf")).deriveFont(30f);
        } catch (Exception e) {
            System.out.println("Error loading font: " + e.getMessage());
            arcadeFont = new Font("Arial", Font.BOLD, 28);
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;


        // 1. Label Trạng thái (Thắng/Thua)
        statusLabel = new JLabel("Game Over!");
        statusLabel.setFont(arcadeFont.deriveFont(70f));
        statusLabel.setForeground(Color.RED);
        gbc.gridy = 0;
        add(statusLabel, gbc);

        // 2. Label Điểm
        scoreLabel = new JLabel("Your Score: ", SwingConstants.CENTER);
        scoreLabel.setFont(arcadeFont.deriveFont(30f));
        scoreLabel.setForeground(Color.WHITE);
        gbc.gridy = 1;
        add(scoreLabel, gbc);
        // Label High Score
        highScoreLabel = new JLabel("High Score: ", SwingConstants.CENTER);
        highScoreLabel.setFont(arcadeFont.deriveFont(30f));
        highScoreLabel.setForeground(Color.WHITE);
        gbc.gridy = 2;
        add(highScoreLabel, gbc);

        // 3. Nút Play Again
        Button playAgainButton = new Button("Play Again");
        playAgainButton.addActionListener(e -> manager.restartGame());
        gbc.gridy = 3;
        add(playAgainButton, gbc);

        // 4. Nút Main Menu
        Button mainMenuButton = new Button("Main Menu");
        mainMenuButton.addActionListener( e -> manager.showMenuAtEndGame());
        gbc.gridy = 4;
        add(mainMenuButton, gbc);

        // 5. Nút Exit
        Button exitButton = new Button("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        gbc.gridy = 5;
        add(exitButton, gbc);
    }

    public void updateResults(String status, int score, int highScore) {
        statusLabel.setText(status);
        scoreLabel.setText("Your Score: " + score);
        highScoreLabel.setText("High Score: " + highScore);

    }

}