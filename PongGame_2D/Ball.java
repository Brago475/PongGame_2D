package src;
import java.awt.*;
import java.util.Random;

public class Ball {

    private int x, y, cx, cy, initialCx, initialCy, initialSpeed, speed, size;
    private Color color;
    private static final Color[] COLORS = {Color.YELLOW, Color.WHITE, Color.RED, Color.MAGENTA, Color.GREEN, Color.CYAN, Color.BLUE};
    private Random rand = new Random();

    
    public Ball(int x, int y, int cx, int cy, int speed, Color color, int size) {
        this.x = x;
        this.y = y;
        this.cx = cx;
        this.cy = cy;
        this.initialCx = cx;  
        this.initialCy = cy;
        this.initialSpeed = speed; 
        this.speed = speed * 2;   
        this.color = color;
        this.size = size;
    }

   
    public void paint(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, size, size);
    }

    
    public void moveBall() {
        x += cx;
        y += cy;
    }

   
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

   
    public void increaseSpeed(int multiplier) {
        speed *= multiplier;
        cx = (cx / Math.abs(cx)) * speed;
        cy = (cy / Math.abs(cy)) * speed;
    }

    
    public void increaseSpeed() {
        increaseSpeed(2);
    }

   
    public void reset(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        this.cx = initialCx;      
        this.cy = initialCy;      
        this.speed = initialSpeed;
    }

    
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

    
    private void changeColor() {
        this.color = COLORS[rand.nextInt(COLORS.length)];
    }

    
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }
    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
    public int getSize() { return size; }
}
