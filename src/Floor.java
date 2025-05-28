import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Floor {

    private int xPos;
    private int yPos;
    private int width;
    private final BufferedImage platform;

    public Floor(int xPos, int yPos, BufferedImage img, int width) {
        this.width = width;
        this.xPos = xPos;
        this.yPos = yPos;
        if(img == null) {
            try {
                platform = ImageIO.read(new File("src/Backgrounds/Floor1.png"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            platform = img;
        }
    }

    public int getWidth() {
        return width;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setWidth(int width) {
        this.width = width;
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
        return new Rectangle(xPos, yPos, width, imageHeight);
    }

    public void drawPlatform(Graphics g){
        g.drawImage(platform, xPos, yPos, width, getPlatformImage().getHeight(),null);
    }

    public void drawPlatform(Graphics g, int width, int height){
        g.drawImage(platform, xPos, yPos, width, height, null);
    }
}