package core;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Renderer extends JPanel implements ActionListener, KeyListener {
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;

    private GameManager gameManager;
    private Timer timer;
    private final int FPS = 60;
    private boolean isRunning = false;
    private boolean leftPressed, rightPressed = false;
    private MenuManager menuManager;


    public Renderer(MenuManager menuManager) {
        this.menuManager = menuManager;
        gameManager = new GameManager();
        gameManager.setMenuManager(menuManager);
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(Color.CYAN);
        setFocusable(true);
        setDoubleBuffered(true);
        addKeyListener(this);

        timer = new Timer(1000 / FPS, this);
    }

    public void startGameLoop() {
        if (!isRunning) {
            isRunning = true;
            timer.start();
        }
    }

    public void stopGameLoop() {
        if (isRunning) {
            isRunning = false;
            timer.stop();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (!gameManager.isPaused()) {
            gameManager.updateGame();
        }

        String gameState = gameManager.getGameState();
        if (gameState.equals("GAME OVER")) {
            stopGameLoop();
            String status = "GAME OVER";
            menuManager.showEndGame(status, gameManager.getScore());
        }

        else if (gameState.equals("COMPLETE LEVEL")) {
            stopGameLoop();
            menuManager.levelComplete();
        }

//        else if (gameState.equals("START")) {
//
//            menuManager.nextLevel();
//            startGameLoop();
//        }

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        gameManager.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        gameManager.keyReleased(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (gameManager != null) {
            gameManager.render(g, this);
        }
    }

    public GameManager getGameManager() {
        return gameManager;
    }

}