package powerups;

import core.GameManager;
import objects.GameObject;
import objects.MoveableObject;

import java.awt.image.BufferedImage;

public abstract class PowerUp extends MoveableObject{

    public PowerUp(int x, int y, int width, int height, BufferedImage image){
        super(x, y, width, height);
        this.setImage(image);
        this.setDy(2);   //fall velocity
        this.setDx(0);
    }

    @Override
    public void update() {
        move();
    }

    public abstract void activate(GameManager gameManager);
}
