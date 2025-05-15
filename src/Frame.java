import javax.swing.*;
import java.io.IOException;

public class Frame {
    public Frame() throws IOException {
        JFrame frame = new JFrame("1, 2, spaghetti sauce, 17, train, 4");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(960, 580); // 540 height of image + 40 for window menu bar
        frame.setLocationRelativeTo(null);
        GraphicsPanel panel = new GraphicsPanel();
        frame.add(panel);
        frame.setVisible(true);
    }
}
