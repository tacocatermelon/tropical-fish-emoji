import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Floor {

    private int xPos;
    private int yPos;
    private final BufferedImage platform;

    public Floor(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        try {
            platform = ImageIO.read(new File("src/Floor Test.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public BufferedImage getPlatformImage() {
        return platform;
    }

    public Rectangle platformRect() {
        int imageHeight = getPlatformImage().getHeight();
        int imageWidth = getPlatformImage().getWidth();
        Rectangle rect = new Rectangle((int) xPos, (int) yPos, imageWidth, imageHeight);
        return rect;
    }
}