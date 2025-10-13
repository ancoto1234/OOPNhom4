package objects;

public abstract class MoveableObject extends GameObject {
    protected int dx, dy;

    public MoveableObject(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.dx = 0;
        this.dy = 0;
    }

    public void move() {
        this.x += dx;
        this.y += dy;
    }



}
