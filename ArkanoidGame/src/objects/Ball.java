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
    private double angle; // Góc di chuyển của bóng
    private BufferedImage rotatedImage;

    private static final double MIN_VERTICAL_RATIO = 0.3;

    public Ball(int x, int y, int width, int height, double dx, double dy, int speed) {
        super(x, y, width, height);
        this.dx = dx;
        this.dy = dy;
        this.speed = speed;
        this.angle = 0;
        normalizeSpeed();
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
        final int MARGIN = 2;
        if (x < 0) {
            x = MARGIN;
            dx = Math.abs(dx);
        } else if (x + width >= Renderer.SCREEN_WIDTH) {
            x = Renderer.SCREEN_WIDTH - width - MARGIN;
            dx = -Math.abs(dx);
          //  angle = Math.PI - angle;
        }
        if (y < 0) {
            y = MARGIN;
            dy = Math.abs(dy);

        }

        double minDy = MIN_VERTICAL_RATIO * speed;
        if (Math.abs(dy) < minDy) {
            dy = Math.copySign(minDy, dy == 0 ? -1 : dy); // giữ chiều hiện tại (nếu dy==0 thì đặt hướng lên)
            // điều chỉnh dx sao cho tổng tốc bằng speed
            double newDx = Math.sqrt(Math.max(0, speed * speed - dy * dy));
            dx = Math.copySign(newDx, dx);
        }
        normalizeSpeed();
    }

    public void bounceOffPaddle(Paddle paddle) {
        Rectangle ballBounds = this.getBounds();
        Rectangle paddleBounds = paddle.getBounds();

        double paddleCenter = paddleBounds.getX() + paddleBounds.getWidth() / 2;
        double ballCenter = ballBounds.getX() + ballBounds.getWidth() / 2;
        double relativeIntersect = (ballCenter - paddleCenter) / (paddleBounds.getWidth() / 2);

        relativeIntersect = Math.max(-1, Math.min(1, relativeIntersect));

        double bounceAngle = relativeIntersect * Math.toRadians(60);

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
        x += dx;
        y += dy;

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
        this.dx = 2;
        this.dy = -3;
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


    public double getDx() {
        return dx;
    }


    public double getDy() {
        return dy;
    }
}
