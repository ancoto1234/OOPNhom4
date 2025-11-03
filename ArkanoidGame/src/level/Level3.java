package level;

import core.GameManager;
import java.util.Random;
import objects.*;

public class Level3 extends Level {
    private static final Random random = new Random();

    public Level3(GameManager gm) {
        super(gm);
    }

    @Override
    protected void buildLevel() {
        int startX = 200;
        int startY = 50;
        int brickWidth = 35;
        int brickHeight = 35;
        int rows = 3;
        int cols = 8;

        int normalRate = 60;
        int powerUpRate = 20;
        int explosionRate = 10;

        for (int c = 0;c < cols;c++) {
            if (c != 3 || c != 4) {
                Brick brick = new UnBreakBrick(startX + c * 60, startY + 3 * 60, brickWidth, brickHeight);
                brick.setImage(gm.getImage("unbreak_brick"));
                bricks.add(brick);
            }
        }

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (r == 0 || r == rows - 1 || c == 0
                        || c == cols - 1 || (r == c) || c == rows - r - 1) {
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

}
