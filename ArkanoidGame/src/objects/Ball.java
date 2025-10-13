package objects;

import core.DefaultRenderer;
import org.w3c.dom.css.Rect;
import java.awt.Rectangle;


public class Ball extends MoveableObject{
    private int speed;
    private int dx;
    private int dy;

    public Ball(int x, int y, int width, int height, int dx, int dy, int speed) {
        super(x, y, width, height);
        this.dx = dx;
        this.dy = dy;
        this.speed = speed;
    }

    public void bounceOff() {
        if (this.x <= 0 || this.x + width >= DefaultRenderer.SCREEN_WIDTH) {
            dx = dx*-1;
        } else if (this.y <= 0) {
            dy = dy*-1;
        }
    }
    public boolean checkCollision(GameObject other) {
        Rectangle ballBounds = this.getBounds();
        Rectangle otherBounds = other.getBounds();
        if (ballBounds.intersects(otherBounds)) {
            dy = dy * -1;
            return true;
        }
        return false;

        
    }
    @Override

    public void move() {
        x += dx * speed;
        y += dy * speed;
    }
    
    

}
