import javax.swing.JTabbedPane;
import java.awt.*;

public class GameLauncher {
    static GameWindow window;

    public static void main(String[] args) {
        Resources.readFiles();
        GamePanel game = new GamePanel();
        window = new GameWindow(game);

        window.add(game, BorderLayout.CENTER);
        System.gc();
    }
}