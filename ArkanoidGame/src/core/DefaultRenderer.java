package core;

import java.awt.Color;
import javax.swing.*;

public class DefaultRenderer extends JFrame implements Renderer {
    public void createWindow() {
        setBackground(Color.BLACK);
        setFocusable(true);
        JFrame frame = new JFrame("Arkanoid Game");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null); // Center the window
    }
    
}
