import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable {
    private int i;
    private boolean start;
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
        start = true;
        level = new Level();
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.setPreferredSize(new Dimension(Resources.SCREEN_W, Resources.SCREEN_H));
        this.addKeyListener(new GameController(this, level.getPlayer(), level));
        this.addMouseListener(new GameClick(level));
    }

    public void setStart(boolean b) {
        this.start = b;
        if (b == false) {
            i = 0;
        }
    }
    public boolean isStart() {return start;}

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (start) {
            g.drawImage(Resources.Animation.MENU.get((i / 20) % 4), 0, 0 , null);
        } else {
            level.draw(g);
            trans(g);
        }
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
    private Level level;

    GameController(GamePanel g, Player p, Level level) {
        this.game_panel = g;
        this.player = p;
        this.level = level;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     *  sự kiện ấn phím.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (game_panel.isStart()) {
            game_panel.setStart(false);
            level.setPause(false);
        } else {
            if (e.getKeyCode() == KeyEvent.VK_A) player.leftPressed();
            if (e.getKeyCode() == KeyEvent.VK_S) player.downPressed();
            if (e.getKeyCode() == KeyEvent.VK_D) player.rightPressed();
            if (e.getKeyCode() == KeyEvent.VK_W) player.upPressed();

            if (e.getKeyCode() == KeyEvent.VK_E) level.shoping();
            if (e.getKeyCode() == KeyEvent.VK_SPACE) player.putBomb();
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) level.pause();
        }
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
    private Level level;

    GameClick(Level level) {
        this.level = level;
    }

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
        if (level.isShopping())
            level.getHub().click(e.getX(), e.getY());
    }
}

