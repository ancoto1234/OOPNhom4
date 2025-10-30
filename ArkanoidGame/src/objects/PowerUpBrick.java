package objects;

import core.GameManager;

public class PowerUpBrick extends Brick {

    private boolean hasDroppedPowerUp = false;

    public PowerUpBrick(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.hitPoints = 1;
        this.scoreValue = 5;
    }

    @Override
    public void dropPowerUp(GameManager gameManager) {
        if (!hasDroppedPowerUp) {
            hasDroppedPowerUp = true;
            gameManager.spawnPowerUp(this.x, this.y);

        }
    }


    
}
