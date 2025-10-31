package effects;

import java.awt.*;

public class Particle {
    private double x, y;
    private final double dx;
    private double dy;
    private double life;
    private double alpha;
    private final Color color;
    private final int size;

    public Particle(double x, double y, double dx, double dy, double life, Color color) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.life = life;
        this.color = color;
        this.alpha = 1;
        this.size = (int) (Math.random() * 2) + 3;
    }

    public boolean isAlive() {
        return life > 0;
    }

    public void update() {
        x += dx;
        y += dy;
        dy += 0.1;
        life -= 0.5;
        alpha = Math.max(0, life);

    }

    public void render(Graphics2D g2d) {
        if (life > 0) {
            int a = (int) (alpha * 255);
            a = Math.max(0, Math.min(255, a));
            g2d.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), a));
            g2d.fillRect((int) x, (int) y, size, size);
        }
    }

}