package core;

import java.awt.Color;
import javax.swing.*;

public class DefaultRenderer extends JFrame implements Renderer {
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;
    private JPanel gamePanel;

    public  DefaultRenderer(){
        gamePanel = new JPanel();
        gamePanel.setBackground(Color.black);
        add(gamePanel);

        setTitle("Arkanoid Game");
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    @Override
    public void createWindow() {
        setVisible(true);
    }
}
