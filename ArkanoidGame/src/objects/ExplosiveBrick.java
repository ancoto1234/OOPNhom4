
package objects;

import core.GameManager;
import effects.ExplosionEffects;
import effects.ParticleSystem;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExplosiveBrick extends Brick {

    private final int explosionRadius = 80;
    ParticleSystem particleSystem = new ParticleSystem();


    public ExplosiveBrick(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.hitPoints = 1;
        this.scoreValue = 0;
        this.typeBrick = 4;
    }

    @Override
    public void onDestroy(GameManager gameManager) {
        //Hiệu ứng nổ sáng
        ExplosionEffects explosion = new ExplosionEffects(x + width / 2, y + height / 2);
        gameManager.addExplosion(explosion);

        List<Brick> toRemove = new ArrayList<>();

        Iterator<Brick> it = gameManager.getBricks().iterator();
        while (it.hasNext()) {
            Brick b = it.next();
            if (b == this) continue; // Bỏ qua chính nó

            // Tính khoảng cách từ tâm viên gạch tới viên gạch bị nổ
            int bx = b.getX() + b.getWidth() / 2;
            int by = b.getY() + b.getHeight() / 2;

            double distance = Math.hypot(bx - (x + width / 2), by - (y + height / 2));


            // Nếu trong bán kính của vụ nổ => phá
            if (distance <= explosionRadius) {
                b.takeHits();
                if (b.isDestroyed()) {
                    toRemove.add(b);
                    
                }
            
            }
        }
        gameManager.getBricks().removeAll(toRemove);
        gameManager.getBricks().remove(this);


    }


}
