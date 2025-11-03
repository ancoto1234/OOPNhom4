
package level;

import core.GameManager;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import java.util.List;
import javax.imageio.ImageIO;
import objects.*;

public abstract class Level {
    protected List<Brick> bricks;
    protected GameManager gm;
    protected BufferedImage backgroundImage;

    public Level(GameManager gm) {
        this.gm = gm;
        this.bricks = new ArrayList<>();
        buildLevel();
    }

    protected void loadBackground() {
        try {
            String path = "ArkanoidGame/assets/play_background.png";
            backgroundImage = ImageIO.read(new File(path));
        } catch (Exception e) {
            System.out.println("Cannot load background for " + getClass().getSimpleName());
            backgroundImage = null;
        }
    }

    public void renderBackground(Graphics g, Component observer) {
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, 800, 600, observer);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, observer.getWidth(), observer.getHeight());
        }
    }

    protected abstract void buildLevel();

    public List<Brick> getBricks() {
        return bricks;
    }
}