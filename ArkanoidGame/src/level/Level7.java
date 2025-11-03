package level;

import core.GameManager;
import java.util.Random;
import objects.*;

public class Level7 extends Level {
    private static final Random random = new Random();

    public Level7(GameManager gm) {
        super(gm);
    }

    @Override
    protected void buildLevel() {
        int startX = 0;
        int startY = 40;
        int brickWidth = 45;
        int brickHeight = 45;
        int rows = 9;
        int cols = 15;

        int normalRate = 30;
        int powerUpRate = 15;
        int explosionRate = 25;
        int bonus1Rate = 10;
        int bonus2Rate = 10;
        int bonus3Rate = 10;

        for (int r = 1; r < rows; r++) {
            for (int c = 1; c < cols - 1; c++) {
                int x = startX + c * 55;
                int y = startY + r * 50;

                Brick brick;
                // UnBreakBrick tạo tường chữ U
                if (r == 1 || r == rows - 1 || c == 1 || c == cols - 2) {
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
