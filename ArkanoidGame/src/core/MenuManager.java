package core;

import java.awt.*;
import javax.swing.*;

import menu.EndGamePanel;
import menu.MenuPanel;


public class MenuManager extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private MenuPanel menuPanel;
    private Renderer renderer;
    private EndGamePanel endGamePanel;
    public MenuManager() {
        setTitle("Arkanoid Game");
        setSize(Renderer.SCREEN_WIDTH, Renderer.SCREEN_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setBackground(Color.CYAN);

        cardLayout = new CardLayout();
        setLayout(cardLayout);
        mainPanel = new JPanel(cardLayout);

        menuPanel = new MenuPanel(this);
        renderer = new Renderer();
        renderer.setMenuManager(this);
        endGamePanel = new EndGamePanel(this);

        mainPanel.add(menuPanel, "Menu");
        mainPanel.add(renderer, "Game");
        mainPanel.add(endGamePanel, "EndGame");

        add(mainPanel);
        cardLayout.show(mainPanel, "Menu");
        setVisible(true);
        pack();
        
        

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

    public void exitGame() {
        System.exit(0);
    }

    public void showEndGameScreen(String status, int score) {
        endGamePanel.updateResults(status, score);
        cardLayout.show(mainPanel, "EndGame");
        renderer.stopGameLoop();
    }
}
