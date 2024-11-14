import java.awt.*;
import java.util.Random;

public class Ball {

    private int x, y, cx, cy, initialCx, initialCy, initialSpeed, speed, size;
    private Color color;
    private static final Color[] COLORS = {Color.YELLOW, Color.WHITE, Color.RED, Color.MAGENTA, Color.GREEN, Color.CYAN, Color.BLUE};
    private Random rand = new Random();

    // Ball constructor with an initial speed
    public Ball(int x, int y, int cx, int cy, int speed, Color color, int size) {
        this.x = x;
        this.y = y;
        this.cx = cx;
        this.cy = cy;
        this.initialCx = cx;  // Store initial direction values
        this.initialCy = cy;
        this.initialSpeed = speed; // Store initial speed
        this.speed = speed * 2;    // Start with a higher speed if desired
        this.color = color;
        this.size = size;
    }

    // Paint the ball on the screen
    public void paint(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, size, size);
    }

    // Move the ball according to its current speed and direction
    public void moveBall() {
        x += cx;
        y += cy;
    }

    // Bounce off the edges (top and bottom) by reversing the y-direction
    public void bounceOffEdges(int top, int bottom) {
        if (y > bottom - size || y < top) {
            reverseY();
        }
    }

    public void reverseX() {
        cx *= -1;
    }

    public void reverseY() {
        cy *= -1;
    }

    // Increase speed with a specified multiplier
    public void increaseSpeed(int multiplier) {
        speed *= multiplier;
        cx = (cx / Math.abs(cx)) * speed;
        cy = (cy / Math.abs(cy)) * speed;
    }

    // Overloaded increaseSpeed with a default multiplier of 2
    public void increaseSpeed() {
        increaseSpeed(2);
    }

    // Reset the ball to its initial position, speed, and direction
    public void reset(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        this.cx = initialCx;      // Reset to the initial x direction
        this.cy = initialCy;      // Reset to the initial y direction
        this.speed = initialSpeed; // Reset to the initial speed
    }

    // Bounce off edges and change color on each bounce
    public void bounceOffEdgesWithColorChange(int top, int bottom) {
        if (y > bottom - size || y < top) {
            reverseY();
            changeColor();
        }
        if (x < 0 || x > PongGame.WINDOW_WIDTH - size) {
            reverseX();
            changeColor();
        }
    }

    // Method to change the color to a random one from the COLORS array
    private void changeColor() {
        this.color = COLORS[rand.nextInt(COLORS.length)];
    }

    // Getters and setters for ball position and size
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }
    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
    public int getSize() { return size; }
}
