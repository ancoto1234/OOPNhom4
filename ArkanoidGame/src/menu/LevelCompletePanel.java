
package menu;

import UI.Button;
import core.MenuManager;
import java.awt.*;
import javax.swing.*;


public class LevelCompletePanel extends JPanel {
    private MenuManager manager;
    private Font arcadeFont;

    public LevelCompletePanel(MenuManager manager) {
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

        // Label Trạng thái
        JLabel title = new JLabel("LEVEL COMPLETE");
        title.setFont(arcadeFont.deriveFont(50f));
        title.setForeground(Color.GREEN);
        gbc.gridy = 0;
        add(title, gbc);


        // Nút Next Level
        Button playAgainButton = new Button("Next Level");
        playAgainButton.addActionListener(e -> manager.nextLevel());
        gbc.gridy = 1;
        add(playAgainButton, gbc);

        // Nút Main Menu
        Button mainMenuButton = new Button("Main Menu");
        mainMenuButton.addActionListener( e -> manager.showMenuAtEndGame());
        gbc.gridy = 2;
        add(mainMenuButton, gbc);

        // Nút Exit
        Button exitButton = new Button("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        gbc.gridy = 3;
        add(exitButton, gbc);

    }



}
