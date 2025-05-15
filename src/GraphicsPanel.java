import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GraphicsPanel extends JPanel implements ActionListener, KeyListener, MouseListener {

    private Player player;
    private Timer timer;
    private boolean[] pressedKeys;
    private double[] pressLength;
    private Platform floor;
    private boolean jumpCooldown;

    public GraphicsPanel(){
        player = new Player();
        floor = new Platform(0,500,960,40);
        pressedKeys = new boolean[128]; // 128 keys on keyboard, max keycode is 127
        pressLength = new double[128];
        jumpCooldown = false;
        addKeyListener(this);
        addMouseListener(this);
        setFocusable(true); // this line of code + one below makes this panel active for keylistener events
        requestFocusInWindow(); // see comment above
        timer = new Timer(5, this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(Font.getFont("Spectral"));
        g.drawImage(floor.getPlatformImage(),floor.getxPos(),floor.getyPos(),null);
        g.drawImage(player.getPlayerImage(), player.getxPos(), player.getyPos(), null);
        g.drawString("Position: "+player.getxPos()+ ", " +player.getyPos(),10,20);
        g.drawString("Velocity: "+player.getxVel()+ ", " +player.getyVel(),10,35);
        if(player.isTouching(floor.platformRect())){
            g.drawString("TOUCHING",50,70);
        }
        //g.drawString(getMousePosition().getX()+", "+getMousePosition().getY(),10,50);
        if (pressedKeys[65]) {
            player.faceLeft();
            player.moveLeft();
        }

        // player moves right (D)
        if (pressedKeys[68]) {
            player.faceRight();
            player.moveRight();
        }

        // player moves up (W)
        if (pressedKeys[87]) {
            player.moveUp();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(System.currentTimeMillis()-pressLength[87]>=50&&pressLength[87]!=0){
            pressLength[87] = 0;
            pressedKeys[87] = false;
            jumpCooldown = true;
        }
        player.updatePos();
        player.updateFriction();
        player.updateGravity();
        if((player.playerRect().intersects(floor.platformRect()))||(player.getyPos()>floor.getyPos())){
            player.setyVel(0);
            player.setyPos(floor.getyPos()-player.getPlayerImage().getHeight());
        }
        if(player.getxPos()+player.getxVel()>960-player.getPlayerImage().getWidth()){
            player.setxVel(0);
            player.setxPos(960-player.getPlayerImage().getWidth());
        }else if(player.getxPos()+player.getxVel()<0){
            player.setxVel(0);
            player.setxPos(0);
        }
        if(player.isTouching(floor.platformRect())){
            player.setFalling(false);
            player.setJumping(false);
        }else{
            player.setJumping(player.getyVel() >= 0);
            player.setFalling(player.getyVel() < 0);
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(key==10){
            timer.start();
        }
        if(key==87&&(jumpCooldown)||player.isFalling()||player.isJumping()){
            return;
        }
        pressedKeys[key] = true;
        pressLength[key] = System.currentTimeMillis(); //stores start time
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        pressedKeys[key] = false;
        pressLength[key] = 0;
        if(key == 87) {
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
