package objects;

import java.awt.*;

public abstract class MoveableObject extends GameObject {
    protected int dx, dy;

    public MoveableObject(int x, int y, int width, int height, Image image, int dx, int dy) {
        super(x, y, width, height, image);
        this.dx = dx;
        this.dy = dy;
    }

    public abstract void move();


}
