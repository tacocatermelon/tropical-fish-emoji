import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Level {

    private ArrayList<Platform> platforms;
    private BufferedImage platform;
    private BufferedImage background;
    int[] startPos;

    public Level(int level) {
        startPos = new int[2];
        platforms = new ArrayList<>();
        if(level == 1){
            startPos[0] = 225;
            startPos[1] = height(200);
            try {
                background = ImageIO.read(new File("src/Backgrounds/sky background.png"));
                platform = ImageIO.read(new File("src/Backgrounds/Floor1.png"));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            platforms.add(new Platform(200, height(120), platform, 100, 40));
            platforms.add(new Platform(400, height(150), platform, 200, 30));
            platforms.add(new Platform(800, height(225), platform, 150, 35));
            platforms.add(new Platform(1250, height(300), platform, 100, 40));
            platforms.add(new Platform(800, height(350), platform, 125, 25));
            platforms.add(new Platform(600, height(400), platform, 150, 25));
            platforms.add(new Platform(500, height(460), platform, 60, 25));
            platforms.add(new Platform(350, height(500), platform, 120, 35));
            platforms.add(new Platform(100, height(550), platform, 100, 35));
            platforms.add(new Platform(250, height(675), platform, 100, 20));
        }else if(level == 2){
            startPos[0] = 0;
            startPos[1] = height(25);
            try {
                background = ImageIO.read(new File("src/Backgrounds/sky background.png"));
                platform = ImageIO.read(new File("src/Backgrounds/Floor1.png"));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            platforms.add(new Platform(100, height(150), platform, 200, 30));
        }else if(level == 3){
            startPos[0] = 0;
            startPos[1] = height(25);
            try {
                background = ImageIO.read(new File("src/Backgrounds/sky background.png"));
                platform = ImageIO.read(new File("src/Backgrounds/Floor1.png"));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            platforms.add(new Platform(400, height(150), platform, 200, 30));
        }else if(level == 4){
            startPos[0] = 0;
            startPos[1] = height(25);
            try {
                background = ImageIO.read(new File("src/Backgrounds/sky background.png"));
                platform = ImageIO.read(new File("src/Backgrounds/Floor1.png"));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            platforms.add(new Platform(200, height(150), platform, 200, 30));
        }else if(level == 5){
            startPos[0] = 0;
            startPos[1] = height(25);
            try {
                background = ImageIO.read(new File("src/Backgrounds/sky background.png"));
                platform = ImageIO.read(new File("src/Backgrounds/Floor1.png"));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            platforms.add(new Platform(600, height(150), platform, 200, 30));
        }
    }

    public ArrayList<Platform> getPlatforms() {
        return platforms;
    }

    public BufferedImage getPlatform() {
        return platform;
    }

    public BufferedImage getBackground() {
        return background;
    }

    public int[] getStartPos() {
        return startPos;
    }

    public void drawLevel(Graphics g){
        g.drawImage(background, 0, 0,Frame.getWidth(),Frame.getHeight(), null);
        for(Platform a: platforms){
            g.drawImage(a.getPlatformImage(),a.getxPos(),a.getyPos(),a.getWidth(),a.getHeight(),null);
        }
    }

    private int height(int heightFromBottom){
        return Frame.getHeight()-heightFromBottom;
    }
}
