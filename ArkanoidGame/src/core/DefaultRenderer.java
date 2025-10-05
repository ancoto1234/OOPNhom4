package core;

import java.awt.Color;
import javax.swing.*;

public class DefaultRenderer extends JFrame implements Renderer {
    private JPanel gamePanel;
    private final int WIDTH = 800;
    private final int HEIGHT = 600;

    public  DefaultRenderer(){
        gamePanel = new JPanel();
        gamePanel.setBackground(Color.black);
        add(gamePanel);

        setTitle("Arkanoid Game");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    @Override
    public void createWindow() {
        setVisible(true);
    }
}
