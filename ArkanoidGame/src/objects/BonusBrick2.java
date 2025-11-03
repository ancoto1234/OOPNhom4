package objects;

public class BonusBrick2 extends Brick {
    
    public BonusBrick2(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.hitPoints = 1;
        this.scoreValue = 15;
        this.typeBrick = 5;
        
    }
}
