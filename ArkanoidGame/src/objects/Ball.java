
package objects;

import core.Renderer;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;


public class Ball extends MoveableObject{
    private int speed;
    private double dx;
    private double dy;
    private double angle; // Góc xoay của bóng
    private BufferedImage rotatedImage;

    public Ball(int x, int y, int width, int height, double dx, double dy, int speed) {
        super(x, y, width, height);
        this.dx = dx;
        this.dy = dy;
        this.speed = speed;
        this.angle = 0;
    }

    private void normalizeSpeed() {
        double length = Math.sqrt(dx * dx + dy * dy);
        if (length == 0) {
            return;
        }

        dx = (dx / length) * speed;
        dy = (dy / length) * speed;
    }

    public void bounceOffWall() {
        final int MARGIN = 1;
        if (x <= 0) {
            x = MARGIN;
            dx = -dx;
        } else if (x + width >= Renderer.SCREEN_WIDTH) {
            x = Renderer.SCREEN_WIDTH - width - MARGIN;
            dx = -dx;
            // angle = Math.PI - angle;
        }
        if (y <= 0) {
            y = MARGIN;
            dy = -dy;

        }


        normalizeSpeed();
    }

    public void bounceOffPaddle(Paddle paddle) {
        Rectangle ballBounds = this.getBounds();
        Rectangle paddleBounds = paddle.getBounds();

        double paddleCenter = paddleBounds.getX() + paddleBounds.getWidth() / 2;
        double ballCenter = ballBounds.getX() + ballBounds.getWidth() / 2;
        double relativeIntersect = (ballCenter - paddleCenter) / (paddleBounds.getWidth() / 2);

        // Giới hạn [-1, 1]
        relativeIntersect = Math.max(-1, Math.min(1, relativeIntersect));


        // Làm cho góc tăng dần mượt hơn theo khoảng cách từ tâm
        // Ở giữa (0) thỉ nhỏ, càng xa điểm tâm thì càng lớn
        double curve = Math.sin(relativeIntersect * Math.PI / 2);

        //  Góc lệch tối đa
        double maxBounceAngle = Math.toRadians(80);
        double bounceAngle = curve * maxBounceAngle;

        dx = Math.sin(bounceAngle);
        dy = -speed * Math.cos(bounceAngle);
        normalizeSpeed();


        y = (paddle.getY() - height - 2);

    }

    public void bounceOffBrick(Brick brick) {
        Rectangle ballBounds = this.getBounds();
        Rectangle brickBounds = brick.getBounds();

        int overlapLeft = ballBounds.x + ballBounds.width - brickBounds.x;
        int overlapRight = brickBounds.x + brickBounds.width - ballBounds.x;
        int overlapTop = ballBounds.y + ballBounds.height - brickBounds.y;
        int overlapBottom = brickBounds.y + brickBounds.height - ballBounds.y;

        int minOverLap = Math.min(Math.min(overlapLeft, overlapRight), Math.min(overlapBottom, overlapTop));

        if (minOverLap == overlapLeft || minOverLap == overlapRight) {
            dx = -dx;
            normalizeSpeed();
        } else {
            dy = -dy;
            normalizeSpeed();
        }

    }

    public boolean checkCollision(GameObject other) {
        return this.getBounds().intersects(other.getBounds());
    }
    @Override

    public void move() {
        x += dx * speed;
        y += dy * speed;
        normalizeSpeed();

        angle += 0.2; // Tăng góc để tạo hiệu ứng xoay

        if (angle > 360) {
            angle = 0; // Đặt lại góc khi vượt quá 2π
        }
        rotateImage();
    }

    public void resetBall(Paddle paddle) {
        int ballX = (Renderer.SCREEN_WIDTH - this.width) / 2;
        int ballY = Renderer.SCREEN_HEIGHT - paddle.height - 40 - this.height - 5;

        this.x = ballX;
        this.y = ballY;
        this.dx = 0;
        this.dy = -2;
        this.angle = 0;

        normalizeSpeed();
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

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public void update() {

    }

    @Override
    public double getDx() {
        return dx;
    }

    @Override
    public double getDy() {
        return dy;
    }
}
