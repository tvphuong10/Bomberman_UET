import java.awt.*;
import java.util.Random;

public class Ghost extends Enemy {
    Player player;

    Ghost (int x, int y, Room room, int level) {
        super(x, y, room, level);
        this.animation = Resources.Animation.GHOST.getAnimation();
        this.dead_animation = Resources.Animation.GHOSTDEAD.getAnimation();
    }

    @Override
    protected void hurt() {
        Resources.Sound.GHOST.reStart();
    }

    /**
     * override lai ham condition tra ve true neu tim thay nhan vat.
     * @param x toa do x o dang xet.
     * @param y toa do y o dang xet.
     * @return true neu x, y dung la toa do nhan vat.
     */
    @Override
    protected boolean condition(int x, int y) {
        int x_  = player.x_room;
        int y_  = player.y_room;
        return x == x_ && y == y_;
    }

    /**
     * cap nhat.
     * @param player vi tri nguoi choi (dung cho nhung con can vi tri nguoi choi de di chuyen)
     * @return
     */
    @Override
    protected int update(Player player) {
        this.player = player;
        if (is_dead) {
            if (frame >= 20) {
                int x  = (int) ((location_x + 24) / Resources.BLOCK_SIZE);
                int y  = (int) ((location_y + 48) / Resources.BLOCK_SIZE);
                room.makeCoin(x, y); // chet roi tien
                return -1;
            }
        } else {
            burn();
            if (!up && !right && !down && !left)
            findTheWay(6);
            move();
        }
        return 0;
    }

    @Override
    protected void draw(Graphics g) {
        frame ++;
        if (is_dead) {
            if (frame < 12)  g.drawImage(this.dead_animation[frame / 3] ,location_x, location_y ,width ,height ,null);
        } else {
            if (frame >= 12) frame = 0;
            if (!flip)   g.drawImage(this.animation[frame / 3] ,location_x + width ,location_y ,-width ,height ,null);
            else        g.drawImage(this.animation[frame / 3] ,location_x ,location_y ,width ,height ,null);
        }
    }
}
