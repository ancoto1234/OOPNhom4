package core;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Renderer extends JPanel implements KeyListener {

    private GameManager gameManager;
    private JFrame window;
    private boolean leftPressed, rightPressed = false;


    public Renderer() {
        gameManager = new GameManager();
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        window = new JFrame("Arkanoid");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.add(this);
        window.pack();
        window.setVisible(true);
        window.setLocationRelativeTo(null);
        window.setResizable(false);

        startGameLoop();
    }

    private void startGameLoop() {
        Timer timer = new Timer(16, e -> {
            gameManager.updateGame();
            repaint();
        });
        timer.start();
    }

    public void keyPressed(KeyEvent e) {
        gameManager.keyPressed(e);
    }

    public void keyReleased(KeyEvent e) {
        gameManager.keyReleased(e);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (gameManager != null) {
            gameManager.render(g, this);
        }
    }

}
