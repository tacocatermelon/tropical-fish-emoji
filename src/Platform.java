import java.awt.image.BufferedImage;

public class Platform extends Floor{

    private int height;

    public Platform(int xPos, int yPos, BufferedImage img, int width, int height) {
        super(xPos, yPos, img, width);
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
