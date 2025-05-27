import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Frame {

    private static JFrame frame;

    public Frame() throws IOException {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame = new JFrame("1, 2, spaghetti sauce, 17, train, 4");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(screenSize.width, screenSize.height-100);// 100 for window menu bar
        frame.setLocationRelativeTo(null);
        GraphicsPanel panel = new GraphicsPanel();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.add(panel);
        frame.setVisible(true);
    }

    public static int getWidth(){
        return frame.getWidth();
    }

    public static int getHeight(){
        return frame.getHeight();
    }
}
