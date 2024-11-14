package src;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class PongGame extends JPanel implements KeyListener {
    private String player1Name = "Player 1";
    private String player2Name = "Player 2";
    private boolean player1ControlsLeftPaddle;

    private Ball gameBall;
    private Paddle leftPaddle, rightPaddle;
    private Ball backgroundBall; // New background ball
    private Timer colorChangeTimer; // Timer for color changes

    static final int WINDOW_WIDTH = 640;
    static final int WINDOW_HEIGHT = 480;
    private int player1Score = 0, player2Score = 0;
    private final int winningScore = 30;
    private boolean gameEnded = false;
    private boolean gameStarted = false;

    private JButton playGameButton;
    private JButton noMoreButton;
    private JButton quitButton;
    private JButton restartButton;

    private boolean upPressed = false, downPressed = false;
    private boolean wPressed = false, sPressed = false;

    private Color[] colors = {Color.YELLOW, Color.WHITE, Color.RED, Color.MAGENTA, Color.GREEN, Color.CYAN, 
        Color.BLUE}; // Colors for background ball
        
    private Random rand = new Random();

    public PongGame(String player1Name, String player2Name, boolean player1ControlsLeftPaddle) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.player1ControlsLeftPaddle = player1ControlsLeftPaddle;

        gameBall = new Ball(300, 200, 2, 2, 3, Color.YELLOW, 10);
        leftPaddle = new Paddle(10, 200, 75, 3, Color.WHITE);
        rightPaddle = new Paddle(610, 200, 75, 3, Color.WHITE);

        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setFocusable(true);
        addKeyListener(this);

        setLayout(null);

        // Initialize background ball for interactive effect
        backgroundBall = new Ball(150, 100, 2, 2, 2, colors[0], 15);

        // "Play Game" button to start the game
        playGameButton = new JButton("Play Game");
        playGameButton.setBounds(WINDOW_WIDTH / 2 - 50, WINDOW_HEIGHT / 2 - 50, 100, 40);
        playGameButton.addActionListener(e -> startGame());
        add(playGameButton);

        // "No More" button to exit the application
        noMoreButton = new JButton("No More");
        noMoreButton.setBounds(WINDOW_WIDTH / 2 - 50, WINDOW_HEIGHT / 2 + 10, 100, 40);
        noMoreButton.addActionListener(e -> System.exit(0));
        add(noMoreButton);

        // Quit button for exiting the game mid-play
        quitButton = new JButton("Quit");
        quitButton.setBounds(WINDOW_WIDTH / 2 - 50, WINDOW_HEIGHT - 50, 100, 40);
        quitButton.addActionListener(e -> quitGame());
        quitButton.setFocusable(false);
        quitButton.setVisible(false); // Initially hidden
        add(quitButton);

        // Restart button, hidden initially, shown only at game end
        restartButton = new JButton("Restart");
        restartButton.setBounds(WINDOW_WIDTH / 2 - 50, WINDOW_HEIGHT / 2, 100, 40);
        restartButton.addActionListener(e -> restartGame());
        restartButton.setVisible(false);
        restartButton.setFocusable(false);
        add(restartButton);
    }

    private void startGame() {
        gameStarted = true;
        playGameButton.setVisible(false);
        noMoreButton.setVisible(false);
        quitButton.setVisible(true); // Show the quit button when game starts
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        // If the game hasn't started, show the background ball as a moving, color-changing element
        if (!gameStarted) {
            backgroundBall.paint(g);
            backgroundBall.moveBall();
            backgroundBall.bounceOffEdgesWithColorChange(0, WINDOW_HEIGHT);
            return;
        }

        g.setColor(Color.WHITE);
        int lineHeight = 10;
        int spacing = 10;
        for (int y = 0; y < getHeight(); y += lineHeight + spacing) {
            g.fillRect(getWidth() / 2 - 1, y, 2, lineHeight);
        }

        gameBall.paint(g);
        leftPaddle.paint(g);
        rightPaddle.paint(g);

        g.setFont(new Font("Monospaced", Font.BOLD, 20));
        g.drawString(player1Name, getWidth() / 4 - 40, 30);
        g.setFont(new Font("Monospaced", Font.BOLD, 40));
        g.drawString(String.valueOf(player1Score), getWidth() / 4 - 20, 70);

        g.setFont(new Font("Monospaced", Font.BOLD, 20));
        g.drawString(player2Name, 3 * getWidth() / 4 - 40, 30);
        g.setFont(new Font("Monospaced", Font.BOLD, 40));
        g.drawString(String.valueOf(player2Score), 3 * getWidth() / 4 - 20, 70);

        if (gameEnded) {
            String endMessage;
            if (player1Score == 0 && player2Score == 0) {
                endMessage = "Do you want to play?";
            } else if (player1Score > player2Score) {
                endMessage = "PLAYER 1 Wins!";
            } else {
                endMessage = "PLAYER 2 Wins!";
            }

            drawCenteredText(g, endMessage, getWidth() / 2, getHeight() / 2 - 40);
            restartButton.setVisible(true);
            quitButton.setVisible(false); // Hide quit button at game end
        }
    }

    private void drawCenteredText(Graphics g, String text, int x, int y) {
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        g.drawString(text, x - textWidth / 2, y);
    }

    // Change color of background ball each time it bounces
    private void changeBackgroundBallColor() {
        Color newColor = colors[rand.nextInt(colors.length)];
        backgroundBall.setColor(newColor);
    }

    public void gameLogic() {
        if (gameEnded || !gameStarted) return; // Only run if game is active

        gameBall.moveBall();
        gameBall.bounceOffEdges(0, WINDOW_HEIGHT);

        if (upPressed && rightPaddle.getY() > 0) rightPaddle.moveUp();
        if (downPressed && rightPaddle.getY() < WINDOW_HEIGHT - rightPaddle.getHeight()) rightPaddle.moveDown();
        if (wPressed && leftPaddle.getY() > 0) leftPaddle.moveUp();
        if (sPressed && leftPaddle.getY() < WINDOW_HEIGHT - leftPaddle.getHeight()) leftPaddle.moveDown();

        if (leftPaddle.checkCollision(gameBall)) {
            gameBall.reverseX();
            gameBall.setX(leftPaddle.getX() + Paddle.PADDLE_WIDTH + 1);
        }
        if (rightPaddle.checkCollision(gameBall)) {
            gameBall.reverseX();
            gameBall.setX(rightPaddle.getX() - 10);
        }

        if (gameBall.getX() < 0) {
            player2Score++;
            if (player2Score % 5 == 0) gameBall.increaseSpeed(1);
            checkWinCondition();
            resetPosition();
        } else if (gameBall.getX() > WINDOW_WIDTH) {
            player1Score++;
            if (player1Score % 5 == 0) gameBall.increaseSpeed(1);
            checkWinCondition();
            resetPosition();
        }
    }

    private void checkWinCondition() {
        if (player1Score >= winningScore || player2Score >= winningScore) {
            gameEnded = true;
            restartButton.setVisible(true);
            quitButton.setVisible(false); // Hide Quit button on game end
        }
    }

    private void quitGame() {
        gameEnded = true;
        quitButton.setVisible(false);
        repaint();
    }

    private void restartGame() {
        player1Score = 0;
        player2Score = 0;
        gameEnded = false;
        gameStarted = false;
        
        restartButton.setVisible(false);  // Hide the restart button on game restart
        quitButton.setVisible(false);     // Hide the quit button initially
        
        playGameButton.setVisible(true);
        noMoreButton.setVisible(true);
        
        resetPosition();
        repaint();
    }
    
    public void resetPosition() {
        gameBall.setX(WINDOW_WIDTH / 2);
        gameBall.setY(WINDOW_HEIGHT / 2);
        gameBall.reverseX();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> upPressed = true;
            case KeyEvent.VK_DOWN -> downPressed = true;
            case KeyEvent.VK_W -> wPressed = true;
            case KeyEvent.VK_S -> sPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> upPressed = false;
            case KeyEvent.VK_DOWN -> downPressed = false;
            case KeyEvent.VK_W -> wPressed = false;
            case KeyEvent.VK_S -> sPressed = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}
