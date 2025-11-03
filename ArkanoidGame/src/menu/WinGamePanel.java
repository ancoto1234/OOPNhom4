package menu;

import UI.Button;
import core.MenuManager;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;

public class WinGamePanel extends JPanel {
    private MenuManager manager;
    private BufferedImage backgroundImage;
    private Font arcadeFont;
    private JLabel scoreLabel;
    private JLabel highScoreLabel;

    public WinGamePanel(MenuManager manager) {
        this.manager = manager;
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);

        try {
            
            arcadeFont = Font.createFont(Font.TRUETYPE_FONT, new java.io.File("ArkanoidGame/assets/font.ttf")).deriveFont(30f);
            backgroundImage = ImageIO.read(new File("ArkanoidGame/assets/background.png"));

        } catch (Exception e) {
            System.out.println("Error loading font: " + e.getMessage());
            arcadeFont = new Font("Arial", Font.BOLD, 28);
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15,15,15,15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //Label trạng thái chiến thắng
        JLabel winLabel = new JLabel("CONGRATULATIONS!", SwingConstants.CENTER);
        winLabel.setFont(arcadeFont.deriveFont(60f));
        winLabel.setForeground(Color.YELLOW);
        gbc.gridy = 0;
        add(winLabel, gbc);

        //Label Phụ
//        JLabel subLabel = new JLabel("You have successfully completed the game!", SwingConstants.CENTER);
//        subLabel.setFont(arcadeFont.deriveFont(25f));
//        subLabel.setForeground(Color.WHITE);
//        gbc.gridy = 1;
//        add(subLabel, gbc);

        scoreLabel = new JLabel("Final Score: 0", SwingConstants.CENTER);
        scoreLabel.setFont(arcadeFont.deriveFont(30f));
        scoreLabel.setForeground(Color.WHITE);
        gbc.gridy = 1;
        add(scoreLabel, gbc);

        highScoreLabel = new JLabel("High Score: ", SwingConstants.CENTER);
        highScoreLabel.setFont(arcadeFont.deriveFont(30f));
        highScoreLabel.setForeground(Color.WHITE);
        gbc.gridy = 2;
        add(highScoreLabel, gbc);

        Button playAgainButton = new Button("Play Again");
        playAgainButton.addActionListener(e -> manager.restartGame());
        gbc.gridy = 3;
        add(playAgainButton, gbc);

        Button mainMenuButton = new Button("Main Menu");
        mainMenuButton.addActionListener( e -> manager.showMenuAtEndGame());
        gbc.gridy = 4;
        add(mainMenuButton, gbc);

        Button exitButton = new Button("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        gbc.gridy = 5;
        add(exitButton, gbc);

    }

    public void updateScore(int score, int highScore) {
        scoreLabel.setText("Final Score: " + score);
        highScoreLabel.setText("High Score: " + highScore);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        }
    }
}
