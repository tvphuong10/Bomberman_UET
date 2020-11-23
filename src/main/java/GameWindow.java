import javax.swing.JFrame;

import java.awt.*;

@SuppressWarnings("serial")
public class GameWindow extends JFrame {

    GameWindow(GamePanel game) {
        this.setTitle("bomberman");
        this.setLayout(new BorderLayout());
        this.add(game, BorderLayout.CENTER);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(Resources.SCREEN_W, Resources.SCREEN_H));
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
