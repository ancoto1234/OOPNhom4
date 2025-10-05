package core;

import java.awt.Color;
import javax.swing.*;

public class DefaultRenderer extends JFrame implements Renderer {
    private JPanel gamePanel;

    public  DefaultRenderer(){
        gamePanel = new JPanel();
        gamePanel.setBackground(Color.black);
        add(gamePanel);

        setTitle("Arkanoid Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    @Override
    public void createWindow() {
        setVisible(true);
    }
}
