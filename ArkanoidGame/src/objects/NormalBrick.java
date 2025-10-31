

package objects;

public class NormalBrick extends Brick{

    public NormalBrick(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.hitPoints = 1;
        this.scoreValue = 5;

    }

    @Override
    public void update() {


    }

}