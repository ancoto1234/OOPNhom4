

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
        int brickWidth = 30;
        int brickHeight = 30;
        int rows = 15;
        int cols = 15;

        for (int r = 0; r < rows; r+=2) {
            for (int c = 0; c <= r; c+=2) {
                int x = startX + c * brickWidth;
                int y = startY + r * brickHeight;
                Brick brick = new NormalBrick(x, y, brickWidth, brickHeight);
                brick.setImage(gm.getImage("brick"));
                bricks.add(brick);
            }
        }
    }

}
