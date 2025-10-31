package powerups;

import core.GameManager;
import objects.Paddle;

import java.awt.image.BufferedImage;

public class ExpandPadllePowerUp extends PowerUp {
    private static final long duration = 3000;

    public  ExpandPadllePowerUp(int x, int y, int width, int height, BufferedImage image){
        super(x, y, width, height, image);
    }

    @Override
    public void activate(GameManager gameManager) {
        Paddle paddle = gameManager.getPaddle();
        if (paddle == null) return;

        BufferedImage expandedImage = gameManager.getImage("paddle_expand");
        int newWidth = (int) (gameManager.getOriginalPaddleWidth() * 1.5);

        paddle.setImage(expandedImage);
        paddle.setWidth(newWidth);

        Runnable revertAction = () -> {
            System.out.println("EXPANDED EXPIRED");
            Paddle p = gameManager.getPaddle();
            if (p != null){
                p.setImage(gameManager.getOriginalPaddleImage());
                p.setWidth(gameManager.getOriginalPaddleWidth());
            }
        };

        gameManager.addActiveEffect("EXPAND_PADDLE", duration, revertAction);

        System.out.println("EXPANDING PADDLE");
    }
}