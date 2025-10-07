package objects;

import java.awt.*;

public abstract class GameObject {
    protected int x, y;
    protected int width, height;
    protected Image image;

    public GameObject (int positionX, int positionY, int width, int height, Image image) {
        this.x =  positionX;
        this.y = positionY;
        this.width = width;
        this.height = height;
        this.image = image;
    }

    public abstract void update ();

    public void render (Graphics g) {
        if (image != null) {
            g.drawImage(image, x, y, null);
        }
    }

    public boolean checkCollision(GameObject other) {
        return this.x < other.x + other.width &&
                this.x + this.width > other.x &&
                this.y < other.y + other.height &&
                this.y + this.height > other.y;
    }

}
