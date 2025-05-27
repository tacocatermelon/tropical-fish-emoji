import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Floor {

    private int xPos;
    private int yPos;
    private final BufferedImage platform;

    public Floor(int xPos, int yPos, BufferedImage img) {
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
        return new Rectangle(xPos, yPos, imageWidth, imageHeight);
    }

    public void drawPlatform(Graphics g, int width){
        g.drawImage(platform, xPos, yPos, width, getPlatformImage().getHeight(),null);
    }

    public void drawPlatform(Graphics g, int width, int height){
        g.drawImage(platform, xPos, yPos, width, height, null);
    }
}