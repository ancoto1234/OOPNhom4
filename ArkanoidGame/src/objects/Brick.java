package objects;

import java.awt.*;

public abstract class Brick extends GameObject {
    protected int hitPoints;
    protected String type;

    public Brick(int positionX, int positionY, int width, int height, Image image, int hitPoints, String type) {
        super(positionX, positionY, width, height, image);
        this.hitPoints = hitPoints;
        this.type = type;
    }

    public void takeHits (Ball ball) {
        if (this.checkCollision(ball)) {
            this.hitPoints--;
        }
    }

    public boolean isDestroyed () {
        return hitPoints == 0;
    }
}