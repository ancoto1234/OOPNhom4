
package level;

import core.GameManager;
import java.util.Random;
import objects.*;

public class Level1 extends Level {

    private static final Random random = new Random();

    public Level1(GameManager gm) {
        super(gm);
    }

    @Override
    protected void buildLevel() {
        int startX = 0;
        int startY = 40;
        int brickWidth = 40;
        int brickHeight = 40;
        int rows = 5;
        int cols = 20;

        int normalRate = 60;
        int powerUpRate = 20;
        int explosionRate = 10;
        // int bonusRate = 10;

        for (int r = 1; r < rows; r++) {
            for (int c = 1; c < cols - 1; c++) {

                int x = startX + c * brickWidth;
                int y = startY + r * brickHeight;
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