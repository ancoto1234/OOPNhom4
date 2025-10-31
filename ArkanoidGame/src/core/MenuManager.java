package core;

import java.awt.*;
import javax.swing.*;
import menu.EndGamePanel;
import menu.LevelCompletePanel;
import menu.MenuPanel;
import menu.PausePanel;


public class MenuManager extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private MenuPanel menuPanel;
    private Renderer renderer;
    private EndGamePanel endGamePanel;
    private LevelCompletePanel levelCompletePanel;
    private PausePanel pausePanel;

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
        pausePanel = new PausePanel(this);

        mainPanel.add(menuPanel, "Menu");
        mainPanel.add(renderer, "Game");
        mainPanel.add(endGamePanel, "EndGame");
        mainPanel.add(levelCompletePanel, "CompleteLevel");
        mainPanel.add(pausePanel, "Pause");

        setGlassPane(new PauseGlassPane(this));
        getGlassPane().setVisible(false);

        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        showMenu();
        setVisible(true);
    }

    public void showMenu() {
        getGlassPane().setVisible(false);
        cardLayout.show(mainPanel, "Menu");
        renderer.stopGameLoop();
    }

    public void showMenuAtPauseGame() {
        getGlassPane().setVisible(false);
        renderer.stopGameLoop();
        renderer.getGameManager().resetGame();
        cardLayout.show(mainPanel, "Menu");
    }

    public void showPauseMenu() {
        renderer.stopGameLoop();
        getGlassPane().setVisible(true);
    }

    public void resumeGame() {
        getGlassPane().setVisible(false);
        cardLayout.show(mainPanel, "Game");
        renderer.requestFocusInWindow();
        renderer.startGameLoop();
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

    public Renderer getRenderer() {
        return renderer;
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    class PauseGlassPane extends JComponent {
        private MenuManager manager;
        private PausePanel pausePanel;

        public PauseGlassPane(MenuManager manager) {
            this.manager = manager;
            this.pausePanel = new PausePanel(manager);
            setLayout(new BorderLayout());
            add(pausePanel, BorderLayout.CENTER);
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {

        }
    }


}