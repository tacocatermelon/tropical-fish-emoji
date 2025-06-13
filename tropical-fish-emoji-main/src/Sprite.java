import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Sprite {

    private Sprite(){}

    public static BufferedImage loadSprite(String file) {

        BufferedImage sprite = null;

        try {
            sprite = ImageIO.read(new File("res/" + file + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sprite;
    }

    public static BufferedImage getSprite(int xGrid, int yGrid, BufferedImage spritesheet) {

        if (spritesheet == null) {
            spritesheet = loadSprite("AnimationSpriteSheet");
        }

        return spritesheet.getSubimage(xGrid * 40, yGrid * 36, 40, 36);
    }
}
