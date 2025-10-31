
package effects;

import java.awt.*;

public class ExplosionEffects {
    private int x,y;
    private double radius;
    private double maxRadius;
    private long startTime;
    private long duration;
    private boolean active = true;
    private Color coreColor;
    private Color smokeColor;


    public ExplosionEffects(int x, int y) {
        this.x = x;
        this.y = y;
        this.radius = 0;
        this.maxRadius = 120;
        this.duration = 500;
        this.startTime = System.currentTimeMillis();
        this.radius = 0;
        this.coreColor = new Color(255, 230, 120);
        this.smokeColor = new Color(80, 80, 80);
    }

    public void update() {
        if (!active) return;

        long elapsed = System.currentTimeMillis() - startTime;
        if (elapsed > duration) {
            active = false;
            return;
        }

        double progress = elapsed / (double) duration;
        radius = maxRadius * progress;
    }

    public void render(Graphics2D g2d) {
        if (!active) return;

        long elapsed = System.currentTimeMillis() - startTime;
        float alpha = 1f - (float) elapsed / duration;
        alpha = Math.max(0, Math.min(1, alpha));

        // Core sáng trong
        int a1 = (int) (255 *alpha);
        g2d.setColor(new Color(coreColor.getRed(), coreColor.getGreen(), coreColor.getGreen(), a1));
        g2d.fillOval((int) (x - radius / 3), (int) (y - radius / 3), (int) (radius * 2/3), (int) (radius * 2/3));

        // Khói mờ ngoài
        int a2 = (int) (120 * alpha);
        g2d.setColor(new Color(smokeColor.getRed(), smokeColor.getGreen(), smokeColor.getBlue(),a2));
        g2d.fillOval((int) (x - radius / 2), (int) (y - radius / 2), (int) (radius), (int) (radius));


    }

    public boolean isActive() {
        return active;
    }

}
