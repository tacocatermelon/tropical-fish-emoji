import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GraphicsPanel extends JPanel implements ActionListener, KeyListener, MouseListener {

    private Player player;
    private Timer timer;
    private boolean[] pressedKeys;
    private double[] pressLength;
    private Floor floor;
    private boolean jumpCooldown;
    private ArrayList<Platform> platforms;
    private Level[] levels;
    private int currentLevel;
    private boolean flyMode;

    public GraphicsPanel() throws IOException {
        currentLevel = 1;
        flyMode = false;
        platforms = new ArrayList<>();
        player = new Player();
        floor = new Floor(0, 0, null, Frame.getWidth());
        pressedKeys = new boolean[128]; // 128 keys on keyboard, max keycode is 127
        pressLength = new double[128];
        levels = new Level[]{new Level(1), new Level(2), new Level(3), new Level(4), new Level(5)};
        jumpCooldown = false;
        addKeyListener(this);
        addMouseListener(this);
        setFocusable(true);
        requestFocusInWindow();
        timer = new Timer(1, this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(currentLevel == 1) {
            floor.setxPos(0);
            floor.setyPos(Frame.getHeight()-floor.getPlatformImage().getHeight());
            floor.drawPlatform(g);
        }else{
            floor.setxPos(-100000000);
            floor.setyPos(100000000);
        }
        levels[currentLevel-1].drawLevel(g);

        if(player.isFacingRight()) {
            g.drawImage(player.getAnimation().getSprite(), player.getxPos(), player.getyPos(), null);
        }else{
            g.drawImage(player.getAnimation().getSprite(),player.getxPos()+player.getAnimation().getSprite().getWidth(),player.getyPos(),player.getAnimation().getSprite().getWidth()*-1,player.getAnimation().getSprite().getHeight(),null);
        }

        g.drawString("Position: " + player.getxPos() + ", " + player.getyPos(), 10, 20);
        g.drawString("Velocity: " + player.getxVel() + ", " + player.getyVel(), 10, 35);

        if(flyMode){
            g.drawString("FLY MODE",10,50);
        }

        //g.drawString(getMousePosition().getX()+", "+getMousePosition().getY(),10,50);

        //player moves left
        if (pressedKeys[65] || pressedKeys[37]) { //37 -- Left, 65 -- A
            if(!flyMode){
                player.faceLeft();
                player.moveLeft();
            }else{
                player.setxPos(player.getxPos()-1);
            }
        }

        // player moves right
        if (pressedKeys[68] || pressedKeys[39]) { // 39 -- Right, 68 -- D
            if(!flyMode) {
                player.faceRight();
                player.moveRight();
            }else{
                player.setxPos(player.getxPos()+1);
            }
        }

        // player moves up
        if (pressedKeys[87] || pressedKeys[38] || pressedKeys[32]) { // 38 -- Up, 87 -- W, 32 -- Space
            if(!flyMode){
                player.moveUp();
            }else{
                player.setyPos(player.getyPos()-1);
            }
        }

        if(pressedKeys[83] || pressedKeys[40] && flyMode){ //40 -- Down, 83 -- S
            player.setyPos(player.getyPos()+1);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(player.getyPos()-player.getAnimation().getSprite().getHeight()>Frame.getHeight() && currentLevel>1){
            currentLevel--;
            player.setyPos(0);
        }else if(player.getyPos()<0 && currentLevel<5){
            currentLevel++;
            player.setxPos(levels[currentLevel-1].getStartPos()[0]);
            player.setyPos(levels[currentLevel-1].getStartPos()[1]);
        }

        platforms = levels[currentLevel-1].getPlatforms();

        if(!flyMode) {
            if (System.currentTimeMillis() - pressLength[87] >= 50 && pressLength[87] != 0) {
                pressLength[87] = 0;
                pressedKeys[87] = false;
                pressedKeys[38] = false;
                pressedKeys[32] = false;
                jumpCooldown = true;
            }
        }

        if(!flyMode) {
            player.updateXPos();
            player.updateFriction();
        }
        for(Platform a : platforms) {
            player.horizCollis(a);
        }

        if(!flyMode) {
            player.updateYPos();
            player.updateGravity();
        }
        for(Platform a : platforms) {
            player.vertcollis(a);
        }

        if ((player.playerRect().intersects(floor.platformRect())) || (player.getyPos() > floor.getyPos())) {
            player.setyVel(0);
            player.setyPos(floor.getyPos() - player.getAnimation().getSprite().getHeight());
        }

        if (player.getxPos() + player.getxVel() > Frame.getWidth() - player.getAnimation().getSprite().getWidth()) {
            player.setxVel(0);
            player.setxPos(Frame.getWidth() - player.getAnimation().getSprite().getWidth());
        } else if (player.getxPos() + player.getxVel() < 0) {
            player.setxVel(0);
            player.setxPos(0);
        }

        if(!flyMode) {
            if (player.isTouching(floor.platformRect()) || player.isTouching(platforms)) {
                player.setFalling(false);
                player.setJumping(false);
                player.setStanding(player.getxVel() < 0.1 && player.getxVel() > -0.1);
            } else {
                player.setStanding(false);
                player.setJumping(player.getyVel() >= 0);
                player.setFalling(player.getyVel() < 0);
            }
        }

        if(!player.setAnimation().equals(player.getAnimation())){
            player.getAnimation().stop();
            player.getAnimation().reset();
        }
        player.getAnimation().start();
        player.getAnimation().update();

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == 10) {
            timer.start();
        }

        if ((key == 87 || key == 38 || key == 32) && (jumpCooldown) || player.isFalling() || player.isJumping()) {
            return;
        }

        pressedKeys[key] = true;
        if (key == 38 || key == 32) {
            pressLength[87] = System.currentTimeMillis();
        }
        pressLength[key] = System.currentTimeMillis(); //stores start time

        if(key == 46 && !flyMode){ //46 -- .
            flyMode = true;
        }else if(key == 46){
            flyMode = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        pressedKeys[key] = false;
        pressLength[key] = 0;

        if (key == 87 || key == 38 || key == 32) {
            pressedKeys[87] = false;
            pressedKeys[38] = false;
            pressedKeys[32] = false;
            pressLength[87] = 0;
            jumpCooldown = false;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}