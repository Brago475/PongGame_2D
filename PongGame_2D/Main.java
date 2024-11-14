package src;
import javax.swing.JFrame;

public class Main extends JFrame {

    public Main() {
        setTitle("Pong"); // Set title
        setResizable(false); // Fix the screen size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the screen

        // Initialize PongGame with default player names and control preferences
        PongGame pongGame = new PongGame("Player 1", "Player 2", true);
        add(pongGame);

        pack(); // Adjust frame size to match PongGame's preferred size
        setVisible(true); // Make the frame visible

        // Start the game update loop
        new Thread(() -> {
            while (true) {
                pongGame.gameLogic();
                pongGame.repaint();
                try {
                    Thread.sleep(10); // Adjust delay for smooth animation
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        new Main();
    }
}
