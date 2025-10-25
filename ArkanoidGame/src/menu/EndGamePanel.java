package menu;

import core.MenuManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class EndGamePanel extends JPanel {
    private MenuManager manager;
    private Font arcadeFont;
    private JLabel statusLabel;
    private JLabel scoreLabel;

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
        gbc.gridx = 0;

        // 1. Label Trạng thái (Thắng/Thua)
        statusLabel = new JLabel("GAME OVER");
        statusLabel.setFont(arcadeFont.deriveFont(50f));
        statusLabel.setForeground(Color.RED);
        gbc.gridy = 0;
        add(statusLabel, gbc);

        // 2. Label Điểm
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(arcadeFont.deriveFont(30f));
        scoreLabel.setForeground(Color.WHITE);
        gbc.gridy = 1;
        add(scoreLabel, gbc);

        // 3. Nút Play Again
        JButton playAgainButton = createMenuButton("Play Again", e -> manager.restartGame());
        gbc.gridy = 2;
        add(playAgainButton, gbc);

        // 4. Nút Main Menu
        JButton mainMenuButton = createMenuButton("Main Menu", e -> manager.showMenuAtEndGame());
        gbc.gridy = 3;
        add(mainMenuButton, gbc);

        // 5. Nút Exit
        JButton exitButton = createMenuButton("Exit", e -> System.exit(0));
        gbc.gridy = 4;
        add(exitButton, gbc);
    }

    public void updateResults(String status, int score) {
        statusLabel.setText(status);
        scoreLabel.setText("Final Score: " + score);

        if (status.equals("CONGRATULATIONS!")) {
            statusLabel.setForeground(Color.GREEN);
        } else {
            statusLabel.setForeground(Color.RED);
        }
    }

    private JButton createMenuButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(arcadeFont);
        button.setForeground(Color.BLUE);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addActionListener(action);
        return button;
    }
}
