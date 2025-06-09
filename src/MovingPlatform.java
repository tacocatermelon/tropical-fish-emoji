import java.awt.image.BufferedImage;

public class MovingPlatform extends Platform {

    private int delay;

    public MovingPlatform(int xPos, int yPos, BufferedImage img, int width, int height, int delay) {
        super(xPos, yPos, img, width, height);
        this.delay = delay;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

}