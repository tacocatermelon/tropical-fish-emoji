import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player {

    private final int MOVE_AMT = 3;
    private int xPos;
    private int yPos;
    private double xVel;
    private double yVel;
    private boolean facingRight;
    private boolean jumping;
    private boolean falling;
    private BufferedImage[][] sprites = new BufferedImage[2][3];

    public Player(){
        xPos = 0;
        yPos = 0;
        xVel = 0;
        yVel = 0;
        facingRight = true;
        jumping = false;
        falling = false;
        try {
            sprites[0][0] = ImageIO.read(new File("src/PlayerSprites/Standing Test.png"));
            sprites[0][1] = ImageIO.read(new File("src/PlayerSprites/Falling Test.png"));
            sprites[0][2] = ImageIO.read(new File("src/PlayerSprites/Jumping Test.png"));
            sprites[1][0] = ImageIO.read(new File("src/PlayerSprites/Standing Test.png"));
            sprites[1][1] = ImageIO.read(new File("src/PlayerSprites/Falling Test.png"));
            sprites[1][2] = ImageIO.read(new File("src/PlayerSprites/Jumping Test.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        
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
    
    public BufferedImage getPlayerImage(){
        int a = 0;
        int b = 0;
        if(!facingRight) {
            a = 1;
        }
        if(falling){
            b = 1;
        } else if (jumping) {
            b = 2;
        }
        return sprites[a][b];
    }

    public Rectangle playerRect() {
        int imageHeight = getPlayerImage().getHeight();
        int imageWidth = getPlayerImage().getWidth();
        Rectangle rect = new Rectangle((int) xPos, (int) yPos, imageWidth, imageHeight);
        return rect;
    }

    public boolean isTouching(Rectangle b){
        Rectangle a = playerRect();
        return (a.getY()+a.getHeight()==b.getY()||a.getY()==b.getY()+b.getHeight())&&((a.getX()>=b.getX())||(a.getX()+a.getWidth()<=b.getX()+b.getWidth()));
    }

}
