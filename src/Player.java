import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Player {

    private int prevX;
    private int prevY;
    private int xPos;
    private int yPos;
    private double xVel;
    private double yVel;
    private boolean facingRight;
    private boolean jumping;
    private boolean falling;
    private boolean standing;
    private Animation[] animations = new Animation[4];
    private BufferedImage walkingSheet;
    private BufferedImage standingSheet;
    private BufferedImage jumpingSheet;
    private BufferedImage fallingSheet;
    private BufferedImage[] walkingRight;
    private Animation walk;
    private Animation stand;
    private Animation jump;
    private Animation fall;
    private Animation animation;

    public Player() {
        xPos = 0;
        yPos = 0;
        xVel = 0;
        yVel = 0;
        facingRight = true;
        jumping = false;
        falling = false;
        standing = false;
        try {
            walkingSheet = ImageIO.read(new File("src/PlayerSprites/Walking SpriteSheet.png"));
            standingSheet = ImageIO.read(new File("src/PlayerSprites/Player Sit Right.png"));
            jumpingSheet = ImageIO.read(new File("src/PlayerSprites/Jumping Still.png"));
            fallingSheet = ImageIO.read(new File("src/PlayerSprites/Falling Still.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        walkingRight = new BufferedImage[]{Sprite.getSprite(0, 0, walkingSheet), Sprite.getSprite(1, 0, walkingSheet), Sprite.getSprite(0, 1, walkingSheet), Sprite.getSprite(1, 1, walkingSheet)};
        walk = new Animation(walkingRight, 10);
        stand = new Animation(new BufferedImage[]{standingSheet}, 10);
        jump = new Animation(new BufferedImage[]{jumpingSheet}, 10);
        fall = new Animation(new BufferedImage[]{fallingSheet}, 10);
        animations[0] = stand;
        animations[1] = walk;
        animations[2] = jump;
        animations[3] = fall;
        animation = stand;
    }

    public Animation getAnimation() {
        return animation;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public double getxVel() {
        return xVel;
    }

    public double getyVel() {
        return yVel;
    }

    public boolean isFacingRight() {
        return facingRight;
    }

    public boolean isStanding() {
        return standing;
    }

    public boolean isJumping() {
        return jumping;
    }

    public boolean isFalling() {
        return falling;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public void setxVel(double xVel) {
        this.xVel = xVel;
    }

    public void setyVel(double yVel) {
        this.yVel = yVel;
    }

    public void faceRight() {
        facingRight = true;
    }

    public void faceLeft() {
        facingRight = false;
    }

    public void setFalling(boolean isFalling) {
        falling = isFalling;
    }

    public void setJumping(boolean isJumping) {
        jumping = isJumping;
    }

    public void setStanding(boolean standing) {
        this.standing = standing;
    }

    public void moveRight() {
        xVel += 0.175;
    }

    public void moveLeft() {
        xVel -= 0.175;
    }

    public void moveUp() {
        yVel += 0.75;
    }

    public void updateGravity() {
        if(!standing) {
            yVel -= 0.1;
        }
    }

    public void updatePos() {
        prevX = xPos;
        prevY = yPos;
        xPos += (int) xVel;
        yPos -= (int) yVel;
    }

    public void updateFriction() {
        if (xVel > 0) {
            if (xVel - 0.1 < 0) {
                xVel = 0;
            } else {
                if (xVel > 5) {
                    xVel -= 0.15;
                } else {
                    xVel -= 0.1;
                }
            }
        } else if (xVel < 0) {
            if (xVel + 0.1 > 0) {
                xVel = 0;
            } else {
                if (xVel < -5) {
                    xVel += 0.15;
                } else {
                    xVel += 0.1;
                }
            }
        }
    }

    public Animation setAnimation() {
        int a = 1;
        if (standing) {
            a = 0;
        } else if (jumping) {
            a = 2;
        } else if (falling) {
            a = 3;
        }
        animation = animations[a];
        return animations[a];
    }

    public Rectangle playerRect() {
        int imageHeight = getAnimation().getSprite().getHeight();
        int imageWidth = getAnimation().getSprite().getWidth();
        return new Rectangle(xPos, yPos, imageWidth, imageHeight);
    }

    public boolean isTouching(Rectangle b) {
        Rectangle a = playerRect();
        return (a.getY() + a.getHeight() == b.getY() || a.getY() == b.getY() + b.getHeight()) && ((a.getX() >= b.getX()) || (a.getX() + a.getWidth() <= b.getX() + b.getWidth()));
    }

    public boolean isTouching(ArrayList<Platform> platforms){
        boolean out = false;
        Rectangle a = playerRect();
        for(Platform pl: platforms){
            Rectangle b = pl.platformRect();
            out = (a.getY() + a.getHeight() == b.getY() || a.getY() == b.getY() + b.getHeight()) && ((a.getX() >= b.getX()) || (a.getX() + a.getWidth() <= b.getX() + b.getWidth()));
        }
        return out;
    }

    public void checkCollision(Platform p){
        if(isNextTo(p)) {
            if (prevX < xPos) {//moving right
                if (xPos + animation.getSprite().getWidth() > p.getxPos() && xPos + animation.getSprite().getWidth() < p.getxPos()+p.getWidth()) {
                    xVel = 0;
                    xPos = p.getxPos() - animation.getSprite().getWidth();
                }
            } else if (prevX > xPos) {//moving left
                if (xPos < p.getxPos() + p.getWidth() && xPos > p.getxPos()) {
                    xVel = 0;
                    xPos = p.getxPos() + p.getWidth();
                }
            }
        }
        if(isAbove(p)) {
            if (prevY < yPos) {//moving down
                if (yPos + animation.getSprite().getHeight() > p.getyPos() && yPos + animation.getSprite().getHeight() < p.getyPos()+p.getHeight()) {
                    yVel = 0;
                    yPos = p.getyPos() - animation.getSprite().getHeight();
                }
            } else if (prevY > yPos) {//moving up
                if (yPos < p.getyPos() + p.getHeight() && yPos > p.getyPos()) {
                    yVel = 0;
                    yPos = p.getyPos() + p.getHeight()+1;
                }
            }
        }
    }

    public boolean isAbove(Platform p){
        boolean out = false;
        if(xPos < p.getxPos() && xPos+animation.getSprite().getWidth() > p.getxPos()){ // left side is further left than platform left
            out =  true;                                                               // right side is further right than platform left
        }else if(xPos+animation.getSprite().getWidth() > p.getxPos()+p.getWidth() && xPos < p.getxPos()+p.getWidth()){ // right side is further than platform right
            out =  true;                                                                                               // left side is further left than platform right
        }else if(xPos > p.getxPos() && xPos+animation.getSprite().getWidth() < p.getxPos()+p.getWidth()){ // left side is further right than platform left
            out = true;                                                                                   // right side is further left than platform right
        }
        return out;
    }

    public boolean isNextTo(Platform p){
        boolean out = false;
        if(yPos < p.getyPos() && yPos+animation.getSprite().getHeight() < p.getyPos()+p.getHeight() && yPos+animation.getSprite().getHeight() > p.getyPos()){ // top edge higher than platform top
            out = true;                                                                                                                                       // bottom edge higher than platform bottom and lower than platform top
        }else if(yPos+animation.getSprite().getHeight() > p.getyPos()+p.getHeight() && yPos > p.getyPos() && yPos < p.getyPos()+p.getHeight()){ //bottom edge lower than platform bottom
            out = true;                                                                                                                         // top edge lower than platform top and higher than platform bottom
        }else if(yPos > p.getyPos() && yPos+animation.getSprite().getHeight() < p.getyPos()+p.getHeight()){ // top edge lower than top edge
            out = true;                                                                                     // bottom edge higher than bottom edge
        }
        return out;
    }
}
