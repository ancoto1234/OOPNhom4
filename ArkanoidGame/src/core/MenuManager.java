
package core;

import java.awt.*;
import javax.swing.*;
import menu.*;


public class MenuManager extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private MenuPanel menuPanel;
    private Renderer renderer;
    private EndGamePanel endGamePanel;
    private LevelCompletePanel levelCompletePanel;
    private PausePanel pausePanel;
    private WinGamePanel winGamePanel;

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
        winGamePanel = new WinGamePanel(this);

        mainPanel.add(menuPanel, "Menu");
        mainPanel.add(renderer, "Game");
        mainPanel.add(endGamePanel, "EndGame");
        mainPanel.add(levelCompletePanel, "CompleteLevel");
        mainPanel.add(pausePanel, "Pause");
        mainPanel.add(winGamePanel, "WinGame");

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
        updateMenuContinueButton();
        cardLayout.show(mainPanel, "Menu");
        renderer.stopGameLoop();

    }

    public void showMenuAtPauseGame() {
        getGlassPane().setVisible(false);
        renderer.stopGameLoop();
        renderer.getGameManager().saveCurrentGame();

        mainPanel.remove(menuPanel);
        menuPanel = new MenuPanel(this);
        mainPanel.add(menuPanel, "Menu");
        //renderer.getGameManager().resetGame();
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

    public void continueGame() {
        cardLayout.show(mainPanel, "Game");
        getGlassPane().setVisible(false);
        renderer.requestFocusInWindow();
        renderer.startGameLoop();
    }

    public void showMenuAtEndGame() {
        renderer.stopGameLoop();
        SaveGameManager.deleteSave();
        updateMenuContinueButton();
        cardLayout.show(mainPanel, "Menu");
    
        renderer.requestFocusInWindow();
    }

    public void showEndGame(String status, int score, int highScore) {
        endGamePanel.updateResults(status, score, highScore);
        cardLayout.show(mainPanel, "EndGame");
        renderer.stopGameLoop();
    }

    public void levelComplete() {
        renderer.stopGameLoop();
        cardLayout.show(mainPanel, "CompleteLevel");
    }

    public void showWinGame(int score, int highScore) {
        SaveGameManager.deleteSave();
        updateMenuContinueButton();
        winGamePanel.updateScore(score, highScore);
        cardLayout.show(mainPanel, "WinGame");
        renderer.stopGameLoop();
    }

    public void nextLevel() {
        cardLayout.show(mainPanel, "Game");
        renderer.requestFocusInWindow();
        renderer.getGameManager().resume();
        renderer.startGameLoop();
    }

    public void startGame(int level) {
        renderer.getGameManager().resetGame();
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
        updateMenuContinueButton();
        System.exit(0);
    }

    public void updateMenuContinueButton() {
        if (menuPanel != null) {
            menuPanel.updateContinueButtonVisibility();
        }
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public JPanel getMainPanel() {
        return mainPanel;
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
