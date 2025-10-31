
package menu;

import UI.Button;
import core.MenuManager;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;

public class MenuPanel extends JPanel {
    private BufferedImage backgroundImage, logoImage;
    private MenuManager manager;
    private Font arcadeFont;
    private JPanel levelPanel;

    public MenuPanel(MenuManager manager) {
        this.manager = manager;
        setLayout(new GridBagLayout());

        try {
            backgroundImage = ImageIO.read(new File("ArkanoidGame/assets/background.png"));
            logoImage = ImageIO.read(new File("ArkanoidGame/assets/logo.png"));

            arcadeFont = Font.createFont(Font.TRUETYPE_FONT, new File("ArkanoidGame/assets/font.ttf")).deriveFont(30f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(arcadeFont);

        } catch (Exception e) {
            System.out.println("Error loading images or font: " + e.getMessage());
            arcadeFont = new Font("Arial", Font.BOLD, 28);
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 0;

        Button startButton = new Button("Start Game");
        Button exitButton = new Button("Exit");

        startButton.addActionListener(e -> startSelectedLevel(1));
        exitButton.addActionListener(e -> System.exit(0));

        add(startButton, gbc);
        gbc.gridy += 1;
        add(exitButton, gbc);

    }


    private void startSelectedLevel(int level) {
        manager.startGame(level);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        }

        if (logoImage != null) {
            g.drawImage(logoImage, 100, -30,600,400, null);
        }
    }
}
