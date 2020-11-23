import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable {
    private int i;
    private boolean running;
    private Thread thread;
    private boolean game_over;
    private Level level;

    GamePanel() {
        super();
        if (this.thread == null) {
            this.thread = new Thread(this, "GameThread");
            this.thread.start();
        }
        i = 0;
        running = true;
        level = new Level();
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.setPreferredSize(new Dimension(Resources.SCREEN_W, Resources.SCREEN_H));
        this.addMouseMotionListener(new GameController(level.getPlayer()));
        this.addMouseListener(new GameClick(level.getPlayer()));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.MAGENTA);
        g.setFont(new Font("Arial",Font.BOLD, 12));
        g.drawString("ahihi", 0, 0);
        level.draw(g);
    }


    /**
     * vòng lặp game được chạy ở luồng đã được khai báo.
     */
    @Override
    public void run() {
        while (running) {
            i++;
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
            }
            level.update();

            this.repaint();

            if (i > 5000) {
                running = false;
            }
        }

        System.exit(0);
    }

    public void escape() {
        this.running = false;
    }

    private void trans(Graphics g) {
        if(i < 20) {
            g.drawImage(Resources.Images.TRANSITIONS.getImage(), 0,- i * 65, null);
        }
    }

    public boolean isGameOver() {
        return game_over;
    }
}

/**
 * class bắt sự kiện.
 */
class GameController implements MouseMotionListener {
    private Player player;

    GameController(Player player) {
        this.player = player;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        player.setXY(e.getX(), e.getY());
    }
}

class GameClick implements MouseListener {
    private Player player;

    GameClick(Player player) {this.player = player;}

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        player.putBomb();
    }
}

