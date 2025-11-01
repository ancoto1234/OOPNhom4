package level;

import core.GameManager;
import java.util.Random;
import objects.*;

public class Level2 extends Level {
    private static final Random random = new Random();
    public Level2(GameManager gm) {
        super(gm);
    }

    @Override
    protected void buildLevel() {
        int startX = 100;
        int startY = 50;
        int brickWidth = 30;
        int brickHeight = 30;
        int rows = 8;
        int cols = 8;

        int normalRate = 60;
        int powerUpRate = 20;
        int explosionRate = 10;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c <= r; c++) {
                int x = startX + c * 60;
                int y = startY + r * 50;
                Brick brick;
                int roll = random.nextInt(100);

                if (roll < normalRate) {
                    brick = new NormalBrick(x, y, brickWidth, brickHeight);
                    brick.setImage(gm.getImage("brick"));
                    bricks.add(brick);
                }
                else if (roll < normalRate + powerUpRate) {
                    brick = new PowerUpBrick(x, y, brickWidth, brickHeight);
                    brick.setImage(gm.getImage("powerup_brick"));
                    bricks.add(brick);
                }
                else if (roll < normalRate + powerUpRate + explosionRate) {
                    brick = new ExplosiveBrick(x, y, brickWidth, brickHeight);
                    brick.setImage(gm.getImage("explosion_brick"));
                    bricks.add(brick);
                }
                else {
                    brick = new BonusBrick1(x, y, brickWidth, brickHeight);
                    brick.setImage(gm.getImage("bonus1_brick"));
                    bricks.add(brick);

                }
            }
        }
    }

}