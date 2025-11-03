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
        int startX = 0;
        int startY = 40;
        int brickWidth = 40;
        int brickHeight = 40;
        int rows = 6;
        int cols = 14;

        int normalRate = 50;
        int powerUpRate = 20;
        int explosionRate = 15;
        int bonus1Rate = 10;
        int bonus2Rate = 5;

        for (int r = 1; r < rows; r++) {
            for (int c = 1; c < cols - 1; c++) {
                int x = startX + c * 60;
                int y = startY + r * 55;

                Brick brick;
                if (r == 2 && (c == 3 || c == 10)) {
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
                } else {
                    brick = new BonusBrick2(x, y, brickWidth, brickHeight);
                    brick.setImage(gm.getImage("bonus2_brick"));
                }
                bricks.add(brick);
            }
        }
    }
}
