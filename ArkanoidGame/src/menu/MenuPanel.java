package menu;

import core.MenuManager;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
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
            // backgroundImage = ImageIO.read(new File("ArkanoidGame/assets/menu_background.png"));
            // logoImage = ImageIO.read(new File("ArkanoidGame/assets/logo.png"));

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
        gbc.gridx = 0;
        gbc.gridy = 0;



        JButton selectLevelButton = createMenuButton("Level", e -> toggleLevelPanel());
        add(selectLevelButton, gbc);
        gbc.gridy += 1;

        JButton startButton = createMenuButton("Start Game", e -> toggleLevelPanel());
        JButton exitButton = createMenuButton("Exit", e -> System.exit(0));

        add(startButton, gbc);
        gbc.gridy += 1;
        add(exitButton, gbc);

        gbc.gridy += 1;
        levelPanel = new JPanel();
        levelPanel.setOpaque(false);
        levelPanel.setVisible(false); // ẩn mặc định
        levelPanel.setLayout(new GridLayout(1, 3, 10, 10));

        JButton level1 = createMenuButton("Level 1", e -> startSelectedLevel(1));
        JButton level2 = createMenuButton("Level 2", e -> startSelectedLevel(2));
        JButton level3 = createMenuButton("Level 3", e -> startSelectedLevel(3));


        levelPanel.add(level1);
        levelPanel.add(level2);
        levelPanel.add(level3);

        add(levelPanel, gbc);
    }

    private void toggleLevelPanel() {
        levelPanel.setVisible(!levelPanel.isVisible());
        revalidate();
        repaint();
    }

    private void startSelectedLevel(int level) {
        manager.startGame(level);
    }

    private JButton createMenuButton(String text, ActionListener action) {
        JButton button = new JButton(text);

        button.setFont(arcadeFont);
        button.setForeground(Color.BLACK);

        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorderPainted(false);

        button.addActionListener(action);

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setForeground(Color.BLUE);
                button.setFont(arcadeFont.deriveFont(34f));
            }

            public void mouseExited(MouseEvent e) {
                button.setForeground(Color.BLACK);
                button.setFont(arcadeFont.deriveFont(30f));
            }
        });

        return button;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        }

        if (logoImage != null) {
            int logoWidth = getWidth() / 2 - logoImage.getWidth() / 2;
            g.drawImage(logoImage, logoWidth, 60,null);
        }
        /*
        g.setFont(arcadeFont.deriveFont(36f));
        g.setColor(Color.BLACK);
        g.drawString("SELECT LEVEL", getWidth() / 2 - 150, 200);

         */
    }





}