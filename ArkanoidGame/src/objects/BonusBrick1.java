
package objects;

public class BonusBrick1 extends Brick{

    public BonusBrick1(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.hitPoints = 1;
        this.scoreValue = 10;
    }

    @Override
    public void update() {

    }

}
