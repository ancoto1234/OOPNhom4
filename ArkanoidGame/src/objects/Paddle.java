package objects;

import java.awt.event.KeyEvent;

public class Paddle extends MoveableObject {
    private final int speed;

    public Paddle(int x, int y, int width, int height, int speed) {
        super(x, y, width, height);
        this.speed = speed;
    }

    public void move_Left() {
        this.x -= speed;
        if (x < 0) {
            x = 0;
        }

    }
    public void move_Right () {
        this.x += speed;
        if (x + width > 800) {
            x = 800 - width;
        }
        
    }


    public void appyPowerUp() {

    }

    @Override
    public void update() {

    }
}
