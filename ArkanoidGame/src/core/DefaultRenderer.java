package core;

import java.awt.Color;
import javax.swing.*;

public class DefaultRenderer extends JFrame implements Renderer {
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;
    public void createWindow() {
        setBackground(Color.BLACK);
        setFocusable(true);
        JFrame frame = new JFrame("Arkanoid Game");
        frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null); // Center the window
    }
    
}
