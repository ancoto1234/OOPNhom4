package objects;

import core.Renderer;
import org.w3c.dom.css.Rect;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;


public class Ball extends MoveableObject{
    private int speed;
    private int dx;
    private int dy;
    private double angle; // Góc di chuyển của bóng
    private BufferedImage rotatedImage;

    public Ball(int x, int y, int width, int height, int dx, int dy, int speed) {
        super(x, y, width, height);
        this.dx = dx;
        this.dy = dy;
        this.speed = speed;
        this.angle = 0;
    }

    public void bounceOff() {
        if (this.x <= 0 || this.x + width >= Renderer.SCREEN_WIDTH) {
            dx = dx * -1;
            angle = Math.PI - angle; // Đảo ngược góc khi va chạm với tường bên
        } else if (this.y <= 0) {
            dy = dy * -1;
            angle = -angle; // Đảo ngược góc khi va chạm với tường trên
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

        angle -= 0.2; // Tăng góc để tạo hiệu ứng xoay

        if (angle >= 2 * Math.PI) {
            angle -= Math.PI * 2; // Đặt lại góc khi vượt quá 2π
        }

        rotateImage();
    }

    private void rotateImage() {
        BufferedImage baseImage = getImage();
        if (baseImage == null) return;

        int w = baseImage.getWidth();
        int h = baseImage.getHeight();

        BufferedImage rotated = new BufferedImage(w, h, baseImage.getType());
        Graphics2D g2d = rotated.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        AffineTransform at = AffineTransform.getRotateInstance(angle, w / 2, h / 2);
        g2d.drawImage(baseImage, at, null);
        g2d.dispose();

        this.rotatedImage = rotated;


    }

    @Override
    public void render(Graphics g, java.awt.Component observer) {
        if (rotatedImage != null) {
            g.drawImage(rotatedImage, x, y, width, height, observer);
        } else {
            super.render(g, observer);
        }
    }

    @Override
    public void update() {

    }
}
