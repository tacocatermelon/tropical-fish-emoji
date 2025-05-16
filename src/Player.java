import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player {

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

    public Player(){
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
            jumpingSheet = ImageIO.read(new File("src/PlayerSprites/Jumping Test.png"));
            fallingSheet = ImageIO.read(new File("src/PlayerSprites/Falling Test.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        walkingRight = new BufferedImage[]{Sprite.getSprite(0, 0, walkingSheet), Sprite.getSprite(1, 0, walkingSheet), Sprite.getSprite(0, 1, walkingSheet), Sprite.getSprite(1, 1, walkingSheet)};
        walk = new Animation(walkingRight,10);
        stand = new Animation(new BufferedImage[]{standingSheet},10);
        jump = new Animation(new BufferedImage[]{jumpingSheet},10);
        fall = new Animation(new BufferedImage[]{fallingSheet},10);
        animations[0] = stand;
        animations[1] = walk;
        animations[2] = jump;
        animations[3] = fall;
        animation = stand;
    }

    public Animation getAnimation(){
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
    
    public void setFalling(boolean isFalling){
        falling = isFalling;
    }
    
    public void setJumping(boolean isJumping){
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

    public void updateGravity(){
        yVel -= 0.1;
    }

    public void updatePos(){
        xPos += (int) xVel;
        yPos -= (int) yVel;
    }

    public void updateFriction(){
        if(xVel>0){
            if(xVel-0.1<0){
                xVel = 0;
            }else{
                if(xVel>5){
                    xVel -= 0.15;
                }else {
                    xVel -= 0.1;
                }
            }
        }else if(xVel<0){
            if(xVel+0.1>0){
                xVel = 0;
            }else{
                if(xVel<-5){
                    xVel += 0.15;
                }else {
                    xVel += 0.1;
                }
            }
        }
    }
    
    public Animation setAnimation(){
        int a = 1;
        if(standing){
            a = 0;
        } else if (jumping) {
            a = 2;
        }else if(falling) {
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

    public boolean isTouching(Rectangle b){
        Rectangle a = playerRect();
        return (a.getY()+a.getHeight()==b.getY()||a.getY()==b.getY()+b.getHeight())&&((a.getX()>=b.getX())||(a.getX()+a.getWidth()<=b.getX()+b.getWidth()));
    }
}
