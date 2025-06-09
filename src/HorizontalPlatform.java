import java.awt.image.BufferedImage;

public class HorizontalPlatform extends MovingPlatform{

    private boolean goingRight;
    private int leftBound;
    private int rightBound;

    public HorizontalPlatform(int xPos, int yPos, BufferedImage img, int width, int height, int delay, boolean goingRight, int leftBound, int rightBound) {
        super(xPos, yPos, img, width, height, delay);
        this.goingRight = goingRight;
        this.leftBound = leftBound;
        this.rightBound = rightBound;
    }

    public boolean isGoingRight() {
        return goingRight;
    }

    public int getLeftBound() {
        return leftBound;
    }

    public int getRightBound() {
        return rightBound;
    }

    public void setGoingRight(boolean goingRight) {
        this.goingRight = goingRight;
    }

    public void setLeftBound(int leftBound) {
        this.leftBound = leftBound;
    }

    public void setRightBound(int rightBound) {
        this.rightBound = rightBound;
    }

    public void updatePlatform(int tick) {
        if (super.getxPos() < leftBound) {
            goingRight = true;
            super.setxPos(leftBound);
        } else if (super.getxPos() + super.getWidth() > rightBound) {
            goingRight = false;
            super.setxPos(rightBound - super.getWidth());
        }
        if (goingRight && tick % super.getDelay() == 0) {
            super.setxPos(super.getxPos() + 1);
        } else if (tick % super.getDelay() == 0) {
            super.setxPos(super.getxPos() - 1);
        }
    }
}