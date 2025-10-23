package objects;

import java.awt.*;
import java.awt.image.BufferedImage;


public abstract class GameObject {
    protected int x, y;
    protected int width, height;
    protected BufferedImage image;

    public GameObject (int positionX, int positionY, int width, int height) {
        this.x =  positionX;
        this.y = positionY;
        this.width = width;
        this.height = height;
    }

    public abstract void update ();

    public void render (Graphics g, Component observer) {
        if (image != null) {
            g.drawImage(image, x, y, width, height, null);
        }
        else {
            g.setColor(Color.WHITE);
            g.fillRect(x, y, width, height);
        }
    }

    public void setImage(BufferedImage img) {
        this.image = img;
    }

    public BufferedImage getImage() {
        return image;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
