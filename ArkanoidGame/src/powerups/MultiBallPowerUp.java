package powerups;

import core.GameManager;

import java.awt.image.BufferedImage;

public class MultiBallPowerUp extends PowerUp{
    public MultiBallPowerUp(int x, int y, int width, int height, BufferedImage image){
        super(x, y, width, height, image);
    }

    @Override
    public void activate(GameManager gameManager) {
        System.out.println("Cloning balls");
        gameManager.activateMultiBall();
    }
}
