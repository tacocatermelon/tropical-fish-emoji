import java.awt.*;
import java.awt.image.BufferedImage;

public class Platform extends Floor{

    private int height;

    public Platform(int xPos, int yPos, BufferedImage img, int width, int height) {
        super(xPos, yPos, img.getSubimage((int) (Math.random()*(img.getWidth()-width)+1), 0, width, height), width);
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Rectangle platformRect() {
        return new Rectangle(super.getxPos(), super.getyPos(), super.getWidth(), height);
    }
}
