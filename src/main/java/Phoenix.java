import java.util.Random;

public class Phoenix  extends Enemy{
    Player player;
    Phoenix(int x, int y, Room room, int level) {
        super(x, y, room, level);
        this.animation = Resources.Animation.ICE.getAnimation();
        //this.dead_animation = Resources.Animation.GHOSTDEAD.getAnimation();
        this.speed = 15;
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
    // _1 _2 _
    @Override
    protected int update(Player player) {
        this.player = player;
        if (!up && !down && !right && !left) {
            Random ran = new Random();
            int r = ran.nextInt(4);
            if (r == 0 && (room.get(x_room - 1, y_room).charAt(0) == ' ' || room.get(x_room - 1, y_room).charAt(0) == 'X')) x_room--;
            if (r == 1 && (room.get(x_room, y_room + 1).charAt(0) == ' ' || room.get(x_room, y_room + 1).charAt(0) == 'X')) y_room++;
            if (r == 2 && (room.get(x_room + 1, y_room).charAt(0) == ' ' || room.get(x_room + 1, y_room).charAt(0) == 'X')) x_room++;
            if (r == 3 && (room.get(x_room, y_room - 1).charAt(0) == ' ' || room.get(x_room, y_room - 1).charAt(0) == 'X')) y_room--;
        }
        move();
        return 0;
    }
}

