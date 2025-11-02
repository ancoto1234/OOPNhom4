package effects;

import java.awt.*;

public interface Effect {
    public abstract void update();
    public abstract void render(Graphics2D g2d);
}
