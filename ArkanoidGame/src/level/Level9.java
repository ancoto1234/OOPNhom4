package level;

import core.GameManager;
import java.util.Random;
import objects.*;

public class Level9 extends Level {
    private static final Random random = new Random();

    public Level9(GameManager gm) {
        super(gm);
    }

    @Override
    protected void buildLevel() {
        int startX = 40;
        int startY = 60;
        int brickWidth = 40;
        int brickHeight = 40;
        int rows = 10;
        int cols = 15;

        int normalRate = 20;
        int powerUpRate = 15;
        int explosionRate = 30;
        int bonus1Rate = 10;
        int bonus2Rate = 10;
        int bonus3Rate = 15;

        // Kim tự tháp: hàng trên ít viên, càng xuống càng rộng
        for (int r = 1; r <= rows; r++) {
            int bricksInRow = r + 4; // simple pyramid growth
            int offset = (cols - bricksInRow) / 2;
            for (int c = 0; c < bricksInRow; c++) {
                int x = startX + (offset + c) * 50;
                int y = startY + r * 45;

                Brick brick;
                // đặt một "nút" UnBreak ở giữa kim tự tháp (hàng 5)
                if (r == 5 && c == bricksInRow / 2) {
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
