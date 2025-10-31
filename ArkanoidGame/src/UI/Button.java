
package UI;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JButton;
;

public class Button extends JButton {

    private boolean isHovered = false;
    private boolean isPressed = false;
    private Font font;
    private BufferedImage normalImg, hoverImg, pressedImg;

    public Button(String text) {
        super(text);

        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("ArkanoidGame/assets/font.ttf")).deriveFont(23f);
            normalImg = ImageIO.read(new File("ArkanoidGame/assets/button.png"));
            hoverImg = ImageIO.read(new File("ArkanoidGame/assets/button_highlighted.png"));
            pressedImg = ImageIO.read(new File("ArkanoidGame/assets/button_disabled.png"));

        } catch (Exception e) {
            font = new Font("Arial", Font.BOLD, 28);
        }

        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setCursor((Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)));
        setForeground(Color.BLACK);
        setFont(font);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
                setForeground(Color.WHITE);
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                isPressed = true;
                setForeground(Color.BLACK);
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isPressed = false;
                repaint();
            }

            public void mouseExited(MouseEvent e) {
                isHovered = false;
                setForeground(Color.BLACK);
            }
        });

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 40);
    }

    @Override
    protected void paintComponent(Graphics g) {
        BufferedImage img = normalImg;
        if (isPressed) img = pressedImg;
        else if (isHovered) img = hoverImg;

        if (img != null) {
            g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
        } else {
            g.setColor(Color.GRAY);
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        // Vẽ text căn giữa
        g.setColor(getForeground());
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(getText());
        int textHeight = fm.getAscent();
        g.drawString(
                getText(),
                (getWidth() - textWidth) / 2,
                (getHeight() + textHeight) / 2 - 4
        );
    }
}