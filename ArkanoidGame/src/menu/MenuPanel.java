

package menu;

import UI.Button;
import core.GameManager;
import core.MenuManager;
import core.Renderer;
import core.SaveGame;
import core.SaveGameManager;
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
    private Renderer renderer;
    private Button continueButton;

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

        Button startButton = new Button("New Game");
        startButton.addActionListener(e -> manager.startGame(1));
        add(startButton, gbc);
        gbc.gridy += 1;

        continueButton = new Button("Continue");
        continueButton.addActionListener(e -> {
            SaveGame saved = SaveGameManager.loadGame();
            GameManager gm = new GameManager();
            // gm.loadImages();
            gm.loadFromSave(saved);
            gm.setPaused(false);
            gm.setMenuManager(manager);
            manager.continueGame();
            manager.getRenderer().setGameManager(gm);                
            manager.getRenderer().requestFocusInWindow();

        });

        add(continueButton, gbc);
        gbc.gridy += 1;


        Button exitButton = new Button("Exit");
        exitButton.addActionListener(e -> System.exit(0)); 
        add(exitButton, gbc);

        updateContinueButtonVisibility();

    }

    public void updateContinueButtonVisibility() {
        boolean hasSave = new File("ArkanoidGame/data/savegame.dat").exists();
        if (continueButton != null) {
            continueButton.setVisible(hasSave);
        }
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
