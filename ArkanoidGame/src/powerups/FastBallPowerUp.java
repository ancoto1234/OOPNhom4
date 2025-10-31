package powerups;

import core.GameManager;
import objects.Ball;
import objects.Paddle;

import java.awt.image.BufferedImage;
import java.util.List;

public class FastBallPowerUp extends PowerUp {
    private static final long duration = 1000;

    public  FastBallPowerUp(int x, int y, int width, int height, BufferedImage image){
        super(x, y, width, height, image);
    }

    @Override
    public void activate(GameManager gameManager) {
        List<Ball> balls = gameManager.getBalls();
        if (balls == null) return;

        for (Ball ball : balls){
            ball.setSpeed(ball.getSpeed() * 2);
        }

        Runnable revertAction = () -> {
            List<Ball> b = gameManager.getBalls();
            if (b != null){
                for (Ball ball : b){
                    ball.setSpeed(ball.getSpeed() / 2);
                }
            }
        };

        gameManager.addActiveEffect("FAST_BALL", duration, revertAction);
        System.out.println("RAGING BALL");
    }
}