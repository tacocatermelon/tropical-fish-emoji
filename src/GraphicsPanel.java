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
    private boolean[] levels;
    private BufferedImage background;

    public GraphicsPanel() throws IOException {
        background = ImageIO.read(new File("src/Backgrounds/sky background.png"));
        platforms = new ArrayList<>();
        player = new Player();
        floor = new Floor(0, 0, null, Frame.getWidth());
        floor.setyPos(Frame.getHeight()-floor.getPlatformImage().getHeight());
        platforms.add(new Platform(200, floor.getyPos()-120, ImageIO.read(new File("src/Backgrounds/Floor1.png")), 100, 40));
        pressedKeys = new boolean[128]; // 128 keys on keyboard, max keycode is 127
        pressLength = new double[128];
        levels = new boolean[5];
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
        g.drawImage(background, 0, 0,Frame.getWidth(),Frame.getHeight(), null);
        floor.drawPlatform(g);
        for(Platform a:platforms){
            a.drawPlatform(g,a.getWidth(),a.getHeight());
        }
        if(player.isFacingRight()) {
            g.drawImage(player.getAnimation().getSprite(), player.getxPos(), player.getyPos(), null);
        }else{
            g.drawImage(player.getAnimation().getSprite(),player.getxPos()+player.getAnimation().getSprite().getWidth(),player.getyPos(),player.getAnimation().getSprite().getWidth()*-1,player.getAnimation().getSprite().getHeight(),null);
        }
        g.drawString("Position: " + player.getxPos() + ", " + player.getyPos(), 10, 20);
        g.drawString("Velocity: " + player.getxVel() + ", " + player.getyVel(), 10, 35);
        if (player.isTouching(floor.platformRect())) {
            g.drawString("TOUCHING", 50, 70);
        }
        for(Platform a:platforms){
            if (player.isTouching(a.platformRect())) {
                g.drawString("TOUCHING", 50, 110);
            }
        }

        //g.drawString(getMousePosition().getX()+", "+getMousePosition().getY(),10,50);

        //player moves left
        if (pressedKeys[65] || pressedKeys[37]) { //37 -- Left, 65 -- A
            player.faceLeft();
            player.moveLeft();
        }

        // player moves right
        if (pressedKeys[68] || pressedKeys[39]) { // 39 -- Right, 68 -- D
            player.faceRight();
            player.moveRight();
        }

        // player moves up
        if (pressedKeys[87] || pressedKeys[38] || pressedKeys[32]) { // 38 -- Up, 87 -- W, 32 -- Space
            player.moveUp();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (System.currentTimeMillis() - pressLength[87] >= 50 && pressLength[87] != 0) {
            pressLength[87] = 0;
            pressedKeys[87] = false;
            pressedKeys[38] = false;
            pressedKeys[32] = false;
            jumpCooldown = true;
        }
        player.updateXPos();
        player.updateFriction();
        player.horizCollis(platforms.get(0));
        player.updateYPos();
        player.updateGravity();
        player.vertcollis(platforms.get(0));
        if ((player.playerRect().intersects(floor.platformRect())) || (player.getyPos() > floor.getyPos())) {
            player.setyVel(0);
            player.setyPos(floor.getyPos() - player.getAnimation().getSprite().getHeight());
        }

        for(Platform a:platforms){

        }

        if (player.getxPos() + player.getxVel() > Frame.getWidth() - player.getAnimation().getSprite().getWidth()) {
            player.setxVel(0);
            player.setxPos(Frame.getWidth() - player.getAnimation().getSprite().getWidth());
        } else if (player.getxPos() + player.getxVel() < 0) {
            player.setxVel(0);
            player.setxPos(0);
        }
        if (player.isTouching(floor.platformRect())||player.isTouching(platforms)) {
            player.setFalling(false);
            player.setJumping(false);
            player.setStanding(player.getxVel() < 0.1 && player.getxVel() > -0.1);
        } else {
            player.setStanding(false);
            player.setJumping(player.getyVel() >= 0);
            player.setFalling(player.getyVel() < 0);
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