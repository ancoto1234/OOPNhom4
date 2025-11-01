

package level;

import core.GameManager;
import objects.*;

public class Level3 extends Level {

    public Level3(GameManager gm) {
        super(gm);
    }

    @Override
    protected void buildLevel() {
        int startX = 200;
        int startY = 50;
        int brickWidth = 35;
        int brickHeight = 35;
        int rows = 5;
        int cols = 5;

        for (int r = 0; r < rows; r+=2) {
            for (int c = 0; c < cols; c+=2) {
                if (r == 0 || r == rows - 2 || r == rows - 1 || c == 0 || c == cols - 2
                        || c == cols - 1 || (r == c) || c == rows - r - 1 || c == rows - r - 2) {
                    int x = startX + c * brickWidth;
                    int y = startY + r * brickHeight;
                    Brick brick = new NormalBrick(x, y, brickWidth, brickHeight);
                    brick.setImage(gm.getImage("brick"));
                    bricks.add(brick);
                }
            }
        }
    }

}
