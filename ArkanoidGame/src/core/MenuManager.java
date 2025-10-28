package core;

import java.awt.*;
import javax.swing.*;
import menu.EndGamePanel;
import menu.LevelCompletePanel;
import menu.MenuPanel;


public class MenuManager extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private MenuPanel menuPanel;
    private Renderer renderer;
    private EndGamePanel endGamePanel;
    private LevelCompletePanel levelCompletePanel;

    public MenuManager() {
        setTitle("Arkanoid Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setBackground(Color.CYAN);
        

        cardLayout = new CardLayout();
        setLayout(cardLayout);
        mainPanel = new JPanel(cardLayout);

        menuPanel = new MenuPanel(this);
        renderer = new Renderer(this);
        endGamePanel = new EndGamePanel(this);
        levelCompletePanel = new LevelCompletePanel(this);


        mainPanel.add(menuPanel, "Menu");
        mainPanel.add(renderer, "Game");
        mainPanel.add(endGamePanel, "EndGame");
        mainPanel.add(levelCompletePanel, "CompleteLevel");
        

        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        showMenu();
        setVisible(true);


    }

    public void showMenu() {
        cardLayout.show(mainPanel, "Menu");
        renderer.stopGameLoop();
    }

    public void showMenuAtEndGame() {
        renderer.stopGameLoop();
        renderer.getGameManager().resetGame();
        cardLayout.show(mainPanel, "Menu");
    }

    public void showEndGame(String status, int score) {
        endGamePanel.updateResults(status, score);
        cardLayout.show(mainPanel, "EndGame");
        renderer.stopGameLoop();
    }

    public void levelComplete() {
        renderer.stopGameLoop();
        cardLayout.show(mainPanel, "CompleteLevel");
    }

    public void nextLevel() {
        cardLayout.show(mainPanel, "Game");
        renderer.requestFocusInWindow();
        renderer.getGameManager().resume();
        renderer.startGameLoop();
    }

    public void startGame(int level) {
        cardLayout.show(mainPanel, "Game");
        renderer.requestFocusInWindow();
        renderer.getGameManager().setLevel(level);
        renderer.getGameManager().setupLevel(level);
        renderer.startGameLoop();
    }


    public void restartGame() {
        int level = renderer.getGameManager().getCurrentLevel();
        renderer.getGameManager().resetGame();
        cardLayout.show(mainPanel, "Game");
        renderer.requestFocusInWindow();
        renderer.startGameLoop();
    }

    public void exitGame() {
        System.exit(0);
    }
}