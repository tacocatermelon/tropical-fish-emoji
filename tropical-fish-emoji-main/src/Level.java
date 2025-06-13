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
            startPos[1] = height(100);

            try {
                background = ImageIO.read(new File("src/Backgrounds/Level 1 background.png"));
                platform = ImageIO.read(new File("src/Backgrounds/Level 1 Platform.png"));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

            //System.out.println(Frame.getWidth());
            //System.out.println(Frame.getHeight());
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
            startPos[0] = 106;
            startPos[1] = height(200);

            try {
                background = ImageIO.read(new File("src/Backgrounds/level 2 background.png"));
                platform = ImageIO.read(new File("src/Backgrounds/Test Floor.png"));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

            platforms.add(new Platform(100, height(150), platform, 200, 30));
            platforms.add(new HorizontalPlatform(200,height(200),platform,150,25,1,true,200,600));
            platforms.add(new Platform(800, height(300), platform, 200, 30));
            platforms.add(new Platform(700, height(400), platform, 125, 15));
            platforms.add(new VerticalPlatform(400, height(500), platform, 165, 25, 1, true, 50, height(450)));
        }else if(level == 3){
            startPos[0] = 433;
            startPos[1] = 578;

            try {
                background = ImageIO.read(new File("src/Backgrounds/level 3 background.jpg"));
                platform = ImageIO.read(new File("src/Backgrounds/Level 3 Platform.png"));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

            platforms.add(new Platform(400, height(150), platform, 200, 30));
            platforms.add(new Platform(750,height(200),platform,50,20));
            platforms.add(new HorizontalPlatform(900,height(350),platform,75,25,1,true,850,1100));
            platforms.add(new VerticalPlatform(1200,height(450),platform,100,20,1,true,height(525),height(450)));
            platforms.add(new Platform(800,height(500),platform,75,20));
            platforms.add(new Platform(650,height(650),platform,65,25));
        }else if(level == 4){
            startPos[0] = 0;
            startPos[1] = height(25);

            try {
                background = ImageIO.read(new File("src/Backgrounds/sky background.png"));
                platform = ImageIO.read(new File("src/Backgrounds/Test Floor.png"));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

            platforms.add(new Platform(200, height(150), platform, 200, 30));
        }else if(level == 5){
            startPos[0] = 0;
            startPos[1] = height(25);

            try {
                background = ImageIO.read(new File("src/Backgrounds/sky background.png"));
                platform = ImageIO.read(new File("src/Backgrounds/Test Floor.png"));
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
            a.drawPlatform(g,a.getWidth(),a.getHeight());
        }
    }

    private int height(int heightFromBottom){
        return Frame.getHeight()-heightFromBottom;
    }

    public void updatePlatforms(int tick){
        for(Platform a:platforms){
            if(a.getClass() == HorizontalPlatform.class){
                ((HorizontalPlatform) a).updatePlatform(tick);
            }
            if(a.getClass() == VerticalPlatform.class){
                ((VerticalPlatform) a).updatePlatform(tick);
            }
        }
    }

}
