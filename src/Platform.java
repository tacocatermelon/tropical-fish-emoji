import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Platform {

    private int xPos;
    private int yPos;
    private int width;
    private int height;
    private final BufferedImage platform;

    public Platform(int xPos, int yPos, int width, int height) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
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