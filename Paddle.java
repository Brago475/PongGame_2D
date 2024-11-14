import java.awt.*;

public class Paddle {

    // Instance variables
    private int height, x, y, initialY, speed;
    private Color color;

    // Width of paddle will always be 15 for this app
    static final int PADDLE_WIDTH = 15;

    // Constructor
    public Paddle(int x, int y, int height, int speed, Color color) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.speed = speed;
        this.color = color;
        this.initialY = y; // Store the initial y position for resetting
    }

    // Paint the paddle
    public void paint(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, PADDLE_WIDTH, height);
    }

    // Move paddle up
    public void moveUp() {
        y -= speed * 2;
    }

    // Move paddle down
    public void moveDown() {
        y += speed * 2;
    }

    // Check if this paddle collides with a ball
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

    // Set a new speed for the paddle
    public void setSpeed(int newSpeed) {
        this.speed = newSpeed;
    }

    // Reset paddle to its original y position
    public void resetPosition() {
        this.y = initialY;
    }

    // Accessors
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
