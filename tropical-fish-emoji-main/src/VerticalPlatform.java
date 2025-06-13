import java.awt.image.BufferedImage;

public class VerticalPlatform extends MovingPlatform{

    private boolean goingUp;
    private int topBound;
    private int bottomBound;

    public VerticalPlatform(int xPos, int yPos, BufferedImage img, int width, int height, int delay, boolean goingUp, int topBound, int bottomBound) {
        super(xPos, yPos, img, width, height, delay);
        this.goingUp = goingUp;
        this.topBound = topBound;
        this.bottomBound = bottomBound;
    }

    public boolean isGoingUp() {
        return goingUp;
    }

    public int getTopBound() {
        return topBound;
    }

    public int getBottomBound() {
        return bottomBound;
    }

    public void setGoingUp(boolean goingUp) {
        this.goingUp = goingUp;
    }

    public void setTopBound(int topBound) {
        this.topBound = topBound;
    }

    public void setBottomBound(int bottomBound) {
        this.bottomBound = bottomBound;
    }

    public void updatePlatform(int tick) {
        if (super.getyPos() < topBound) {
            goingUp = false;
            super.setyPos(topBound);
        } else if (super.getyPos() + super.getHeight() > bottomBound) {
            goingUp = true;
            super.setyPos(bottomBound - super.getHeight());
        }
        if (goingUp && tick % super.getDelay() == 0) {
            super.setyPos(super.getyPos() - 1);
        } else if (tick % super.getDelay() == 0) {
            super.setyPos(super.getyPos() + 1);
        }
    }
}
