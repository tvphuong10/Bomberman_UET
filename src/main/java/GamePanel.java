import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.*;

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
        this.addKeyListener(new GameController(this, level.getPlayer()));
        this.addMouseListener(new GameClick(level.getPlayer()));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //g.drawImage(Resources.Images.BACKGROUND.getImage(), 0, 0, null);
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

            if (i > 10000) {
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
class GameController implements KeyListener {
    private GamePanel game_panel;
    private Player player;

    GameController(GamePanel g, Player p) {
        this.game_panel = g;
        this.player = p;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     *  sự kiện ấn phím.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A) player.leftPressed();
        if (e.getKeyCode() == KeyEvent.VK_S) player.downPressed();
        if (e.getKeyCode() == KeyEvent.VK_D) player.rightPressed();
        if (e.getKeyCode() == KeyEvent.VK_W) player.upPressed();

        if (e.getKeyCode() == KeyEvent.VK_SPACE) player.putBomb();
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) game_panel.escape();
    }

    /**
     * sự kiện thả phím.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A) player.leftReleased();
        if (e.getKeyCode() == KeyEvent.VK_S) player.downReleased();
        if (e.getKeyCode() == KeyEvent.VK_D) player.rightReleased();
        if (e.getKeyCode() == KeyEvent.VK_W) player.upReleased();
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

