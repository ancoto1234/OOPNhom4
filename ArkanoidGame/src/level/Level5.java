package level;

import core.GameManager;
import java.util.Random;
import objects.*;

public class Level5 extends Level {
    private static final Random random = new Random();

    public Level5(GameManager gm) {
        super(gm);
    }

    @Override
    protected void buildLevel() {
        int startX = 0;
        int startY = 50;
        int brickWidth = 40;
        int brickHeight = 40;
        int rows = 8;
        int cols = 14;

        int normalRate = 35;
        int powerUpRate = 20;
        int explosionRate = 20;
        int bonus1Rate = 10;
        int bonus2Rate = 10;
        int bonus3Rate = 5;

        for (int r = 1; r < rows; r++) {
            for (int c = 1; c < cols - 1; c++) {
                int x = startX + c * 60;
                int y = startY + r * 55;

                Brick brick;
                // UnBreakBrick tạo hình chữ T
                if (r == 2 || c == 7) {
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
