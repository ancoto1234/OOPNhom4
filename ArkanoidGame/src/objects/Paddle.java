package objects;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Paddle extends MoveableObject {
    private final int speed;

    public Paddle(int x, int y, int width, int height, Image image, int speed) {
        super(x, y, width, height, image, 0, 0);
        this.speed = speed;
    }

    public void move_Left() {
        this.x -= speed;

    }
    public void move_Right () {
        this.x += speed;
    }

    public void move_KeyBoard(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            move_Left();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT){
            move_Right();
        }
    }

    @Override
    public void move() {

    }

    @Override
    public void update() {

    }

    public void appyPowerUp() {

    }
}
