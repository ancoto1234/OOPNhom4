package objects;

public class UnBreakBrick extends Brick {
    public UnBreakBrick(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.hitPoints = -1;
        this.scoreValue = 0;
        this.typeBrick = 7;
    }
    
}
