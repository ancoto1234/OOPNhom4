
package powerups;

import core.GameManager;
import java.awt.image.BufferedImage;
import objects.MoveableObject;

public abstract class PowerUp extends MoveableObject{

    public int typePowerUp;

    public PowerUp(int x, int y, int width, int height, BufferedImage image){
        super(x, y, width, height);
        this.setImage(image);
        this.setDy(2);   //fall velocity
        this.setDx(0);
    }

    public int getType() {
        return typePowerUp;
    }

    @Override
    public void update() {
        move();
    }

    public abstract void activate(GameManager gameManager);
}