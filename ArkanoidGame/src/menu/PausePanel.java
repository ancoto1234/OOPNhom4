package menu;

import UI.Button;
import core.MenuManager;
import java.awt.*;
import javax.swing.*;

public class PausePanel extends JPanel {
    private MenuManager manager;
    private Font font;

    public PausePanel(MenuManager manager) {
        this.manager = manager;
        setLayout(new GridBagLayout());
        setOpaque(false);

        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new java.io.File("ArkanoidGame/assets/font.ttf")).deriveFont(30f);

        } catch (Exception e) {
            System.out.println("Error loading font: " + e.getMessage());
            font = new Font("Arial", Font.BOLD, 28);
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("GAME PAUSED");
        title.setFont(font);
        title.setForeground(Color.YELLOW);
        gbc.gridy = 0;
        add(title, gbc);

        Button continueButton = new Button("Continue");
        Button restartButton = new Button("Restart Level");
        Button menuButton = new Button("Main Menu");
        Button exitButton = new Button("Exit Game");

        continueButton.addActionListener(e -> {
            manager.getRenderer().getGameManager().startCountdown();
            manager.resumeGame();
        });
        restartButton.addActionListener(e -> {
            manager.restartGame();
            manager.resumeGame();
        });
        menuButton.addActionListener(e -> manager.showMenuAtPauseGame());
        exitButton.addActionListener(e -> System.exit(0));

        gbc.gridy = 1; add(continueButton, gbc);
        gbc.gridy = 2; add(restartButton, gbc);
        gbc.gridy = 3; add(menuButton, gbc);
        gbc.gridy = 4; add(exitButton, gbc);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, getWidth(), getHeight());


        super.paintComponent(g);
    }
}