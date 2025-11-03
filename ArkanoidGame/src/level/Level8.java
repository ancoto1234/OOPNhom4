package level;

import core.GameManager;
import java.util.Random;
import objects.*;

public class Level8 extends Level {
    private static final Random random = new Random();

    public Level8(GameManager gm) {
        super(gm);
    }

    @Override
    protected void buildLevel() {
        int startX = 5;
        int startY = 45;
        int brickWidth = 40;
        int brickHeight = 40;
        int rows = 10;
        int cols = 14;

        int normalRate = 25;
        int powerUpRate = 15;
        int explosionRate = 30;
        int bonus1Rate = 10;
        int bonus2Rate = 10;
        int bonus3Rate = 10;

        for (int r = 1; r < rows; r++) {
            for (int c = 1; c < cols - 1; c++) {
                int x = startX + c * 55;
                int y = startY + r * 50;

                Brick brick;
                // UnBreakBrick: hai cột giữa dày đặc
                if (c == cols/2 || c == cols/2 - 1) {
                    brick = new UnBreakBrick(x, y, brickWidth, brickHeight);
                    brick.setImage(gm.getImage("unbreak_brick"));
                    bricks.add(brick);
                    continue;
                }

                int roll = random.nextInt(100);
                if (roll < normalRate) {
                    brick = new NormalBrick(x, y, brickWidth, brickHeight);
                    brick.setImage(gm.getImage("brick"));
                } else if (roll < normalRate + powerUpRate) {
                    brick = new PowerUpBrick(x, y, brickWidth, brickHeight);
                    brick.setImage(gm.getImage("powerup_brick"));
                } else if (roll < normalRate + powerUpRate + explosionRate) {
                    brick = new ExplosiveBrick(x, y, brickWidth, brickHeight);
                    brick.setImage(gm.getImage("explosion_brick"));
                } else if (roll < normalRate + powerUpRate + explosionRate + bonus1Rate) {
                    brick = new BonusBrick1(x, y, brickWidth, brickHeight);
                    brick.setImage(gm.getImage("bonus1_brick"));
                } else if (roll < normalRate + powerUpRate + explosionRate + bonus1Rate + bonus2Rate) {
                    brick = new BonusBrick2(x, y, brickWidth, brickHeight);
                    brick.setImage(gm.getImage("bonus2_brick"));
                } else {
                    brick = new BonusBrick3(x, y, brickWidth, brickHeight);
                    brick.setImage(gm.getImage("bonus3_brick"));
                }

                bricks.add(brick);
            }
        }
    }
}
