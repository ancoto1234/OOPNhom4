package powerups;

import core.GameManager;
import objects.Ball;
import objects.Paddle;

import java.awt.image.BufferedImage;

public class FastBallPowerUp extends PowerUp {
    private static final long duration = 3000;

    public  FastBallPowerUp(int x, int y, int width, int height, BufferedImage image){
        super(x, y, width, height, image);
    }

    @Override
    public void activate(GameManager gameManager) {
        Ball ball = gameManager.getBall();
        if (ball == null) return;

        ball.setSpeed(ball.getSpeed() * 2);

        Runnable revertAction = () -> {
            Ball b = gameManager.getBall();
            if (b != null){
                ball.setSpeed(ball.getSpeed() / 2);
            }
        };

        gameManager.addActiveEffect("FAST_BALL", duration, revertAction);
        System.out.println("RAGING BALL");
    }
}
