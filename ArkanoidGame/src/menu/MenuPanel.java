package menu;

import core.MenuManager;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;

public class MenuPanel extends JPanel {
    private BufferedImage backgroundImage, logoImage;
    private MenuManager manager;
    private Font arcadeFont;

    public MenuPanel(MenuManager manager) {
        this.manager = manager;
        setLayout(new GridBagLayout());

        try {
            backgroundImage = ImageIO.read(new File("ArkanoidGame/assets/menu_background.png"));
            logoImage = ImageIO.read(new File("ArkanoidGame/assets/logo.png"));
            
            arcadeFont = Font.createFont(Font.TRUETYPE_FONT, new File("ArkanoidGame/assets/ARCADECLASSIC.TTF")).deriveFont(28f);
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
        gbc.gridy = 1;
    

        JButton startButton = createMenuButton("Start Game", e -> manager.startGame());
        JButton exitButton = createMenuButton("Exit", e -> System.exit(0));

        add(startButton, gbc);
        gbc.gridy += 1;
        add(exitButton, gbc);

    }

    private JButton createMenuButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(arcadeFont);
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 0, 0, 150));
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        button.setPreferredSize(new Dimension(300, 50));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addActionListener(action);

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(255, 255, 255, 50));
                button.setForeground(Color.BLACK);
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(0, 0, 0, 150));
                button.setForeground(Color.WHITE);
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
    }





}
