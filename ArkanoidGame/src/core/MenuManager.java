package core;

import javax.swing.*;
import java.awt.*;
import menu.MenuPanel;

public class MenuManager extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private MenuPanel menuPanel;
    private Renderer renderer;
    
    public MenuManager() {
        setTitle("ArkanoidGame");
        setSize(Renderer.SCREEN_WIDTH, Renderer.SCREEN_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        menuPanel = new MenuPanel(this);
        renderer = new Renderer();

        mainPanel.add(menuPanel, "Menu");
        mainPanel.add(renderer, "Game");

        add(mainPanel);
        cardLayout.show(mainPanel, "Menu");
        setVisible(true);

        showMenu();

    }

    public void showMenu() {
        cardLayout.show(mainPanel, "Menu");
        renderer.stopGameLoop();
    }

    public void startGame() {
        cardLayout.show(mainPanel, "Game");
        renderer.requestFocusInWindow();
        renderer.startGameLoop();
    }
}
