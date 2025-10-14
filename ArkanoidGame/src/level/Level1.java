package level;

import core.GameManager;
import objects.*;

public class Level1 extends Level {

    public Level1(GameManager gm) {
        super(gm);
    }

    @Override
    protected void buildLevel() {
        int startX = 50;
        int startY = 80;
        int brickWidth = 55;
        int brickHeight = 20;
        int rows = 5;
        int cols = 11;

        for (int r = 0;r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int x = startX + c * (brickWidth + 5);
                int y = startY + r * (brickHeight + 5);
                Brick brick = new Brick(x, y, brickWidth, brickHeight, 1);
                brick.setImage(gm.getImage("brick"));
                bricks.add(brick);
            }

        }
    }
    
}
