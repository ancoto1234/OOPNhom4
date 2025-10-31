
package objects;

import core.GameManager;
import effects.ExplosionEffects;

public class ExplosiveBrick extends Brick {


    public ExplosiveBrick(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.hitPoints = 1;
        this.scoreValue = 0;
    }

    @Override
    public void onDestroy(GameManager gameManager) {
        //Hiệu ứng nổ sáng
        ExplosionEffects explosion = new ExplosionEffects(x + width / 2, y + height / 2);
        gameManager.addExplosion(explosion);


    }


}
