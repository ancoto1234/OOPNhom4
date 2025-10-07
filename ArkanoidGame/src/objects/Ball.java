package objects;

import java.awt.*;
import core.DefaultRenderer;


public abstract class Ball extends MoveableObject{
    private int speed;
    private int directionX;
    private int directionY;

    public Ball(int x, int y, int width, int height, Image image, int directionX, int directionY) {
        super(x, y, width, height, image, 0, 0);
        this.directionX = directionX;
        this.directionY = directionY;
    }

    @Override
    public void move() {
        x += directionX * speed;
        y += directionY * speed;
    }

    public void bounceOff () {
        if (this.x <= 0 || this.x >= DefaultRenderer.SCREEN_WIDTH) {
            directionX = -directionX;
        } else if (this.y <= 0 || this.y >= DefaultRenderer.SCREEN_HEIGHT) {
            directionY = -directionY;
        }
    }

}
