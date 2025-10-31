

package objects;

import core.GameManager;

public class Brick extends GameObject {
    protected int hitPoints;
    protected String type;
    public int scoreValue;

    public Brick(int x, int y, int width, int height) {
        super(x, y, width, height);
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

    public void dropPowerUp(GameManager gameManager) {

    }
    public void onDestroy(GameManager gameManager) {

    }

    @Override
    public void update() {

    }
}