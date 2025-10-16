package objects;

import java.awt.*;

public class Brick extends GameObject {
    protected int hitPoints;
    protected String type;

    public Brick(int x, int y, int width, int height, int hitPoints) {
        super(x, y, width, height);
        this.hitPoints = hitPoints;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void takeHits() {
        this.hitPoints -= 1;
    }

    public boolean isDestroyed() {
        return this.hitPoints == 0;
    }

    @Override
    public void update() {

    }
}