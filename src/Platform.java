public class Platform extends Floor{

    private int width;
    private int height;

    public Platform(int xPos, int yPos, int width, int height) {
        super(xPos, yPos);
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
