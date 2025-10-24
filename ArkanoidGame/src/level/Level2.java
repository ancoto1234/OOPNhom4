package level;

import core.GameManager;
import objects.*;

public class Level2 extends Level {

    public Level2(GameManager gm) {
        super(gm);
    }

    @Override
    protected void buildLevel() {
        int startX = 100;
        int startY = 50;
        int brickWidth = 42;
        int brickHeight = 20;
        int rows = 16;
        int cols = 16;

        for (int r = 0; r < rows; r+=2) {
            for (int c = 0; c <= r; c+=2) {
                int x = startX + c * brickWidth;
                int y = startY + r * brickHeight;
                Brick brick = new Brick(x, y, brickWidth, brickHeight, 1);
                brick.setImage(gm.getImage("brick"));
                bricks.add(brick);
            }
        }
    }

}
