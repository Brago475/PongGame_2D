
import java.awt.*;

public class Paddle {

    private int height, x, y, initialY, speed;
    private Color color;

    static final int PADDLE_WIDTH = 15;

    public Paddle(int x, int y, int height, int speed, Color color) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.speed = speed;
        this.color = color;
        this.initialY = y; 
    }

    public void paint(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, PADDLE_WIDTH, height);
    }

    public void moveUp() {
        y -= speed * 2;
    }

    public void moveDown() {
        y += speed * 2;
    }

    public boolean checkCollision(Ball b) {
        int rightX = x + PADDLE_WIDTH;
        int bottomY = y + height;

        if (b.getX() > (x - b.getSize()) && b.getX() < rightX) {
            if (b.getY() > y && b.getY() < bottomY) {
                return true;
            }
        }
        return false;
    }

    public void setSpeed(int newSpeed) {
        this.speed = newSpeed;
    }

    public void resetPosition() {
        this.y = initialY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHeight() {
        return height;
    }
}
