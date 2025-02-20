package src;
import javax.swing.JFrame;

public class Main extends JFrame {

    public Main() {
        setTitle("Pong"); 
        setResizable(false); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        PongGame pongGame = new PongGame("Player 1", "Player 2", true);
        add(pongGame);

        pack(); 
        setVisible(true); 

        new Thread(() -> {
            while (true) {
                pongGame.gameLogic();
                pongGame.repaint();
                try {
                    Thread.sleep(10); 
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
