
package effects;

import java.awt.*;
import java.util.*;
import java.util.List;

public class ParticleSystem implements Effect {
    private final List<Particle> particles = new ArrayList<>();

    public void spawnParticles(int x, int y, int width, int height, Color color) {
        for (int i = 0;i < 20;i++) {
            double px = x + Math.random() * width;
            double py = y + Math.random() * height;
            double dx = (Math.random() - 0.5) * 4;
            double dy = (Math.random() - 0.5) * 4;
            double life = 10 + Math.random() * 20;
            particles.add(new Particle(px, py, dx, dy, life, color));
        }
    }

    @Override
    public void update() {
        Iterator<Particle> it = particles.iterator();
        while (it.hasNext()) {
            Particle p = it.next();
            p.update();
            if (!p.isActive()) {
                it.remove();
            }
        }
    }

    public void clearParticles() {
        particles.clear();
    }

    @Override
    public void render(Graphics2D g2d) {
        for (Particle p : particles) {
            p.render(g2d);
        }
    }

}
