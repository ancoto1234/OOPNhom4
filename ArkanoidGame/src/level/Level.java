
package level;

import core.GameManager;
import java.util.*;
import objects.*;

public abstract class Level {
    protected List<Brick> bricks;
    protected GameManager gm;

    public Level(GameManager gm) {
        this.gm = gm;
        this.bricks = new ArrayList<>();
        buildLevel();
    }

    protected abstract void buildLevel();

    public List<Brick> getBricks() {
        return bricks;
    }
}
