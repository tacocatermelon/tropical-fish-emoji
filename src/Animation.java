import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation {

    private boolean stopped;// has animations stopped
    private ArrayList<Img> frames = new ArrayList<>();
    private int frameCount;// Counts ticks for change
    private final int frameDelay;
    private int currentFrame;// animations current frame
    private final int totalFrames;

    public Animation(BufferedImage[] frames, int frameDelay){
        for (int i = 0; i < frames.length; i++) {
            addFrame(frames[i], frameDelay);
        }
        stopped = true;
        frameCount = 0;
        this.frameDelay = frameDelay;
        currentFrame = 0;
        totalFrames = this.frames.size();
    }

    public void start() {
        if (!stopped) {
            return;
        }
        if (frames.isEmpty()) {
            return;
        }
        stopped = false;
    }

    public void stop() {
        if (frames.isEmpty()) {
            return;
        }
        stopped = true;
    }

    public void restart() {
        if (frames.isEmpty()) {
            return;
        }
        stopped = false;
        currentFrame = 0;
    }

    public void reset() {
        this.stopped = true;
        this.frameCount = 0;
        this.currentFrame = 0;
    }

    public void update() {
        if (!stopped) {
            frameCount++;
            if (frameCount > frameDelay) {
                frameCount = 0;
                currentFrame += 1;
            }
            if (currentFrame > totalFrames - 1) {
                currentFrame = 0;
            }else if (currentFrame < 0) {
                currentFrame = totalFrames - 1;
            }
        }
    }

    private void addFrame(BufferedImage frame, int duration) {
        if (duration <= 0) {
            System.err.println("Invalid duration: " + duration);
            throw new RuntimeException("Invalid duration: " + duration);
        }

        frames.add(new Img(frame, duration));
        currentFrame = 0;
    }

    public BufferedImage getSprite() {
        return frames.get(currentFrame).getFrame();
    }

}
