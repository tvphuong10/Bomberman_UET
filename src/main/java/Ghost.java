import java.util.Random;

public class Ghost extends Enemy {
    Player player;

    Ghost (int x, int y, Room room, int level) {
        super(x, y, room, level);
        this.animation = Resources.Animation.GHOST.getAnimation();
        this.dead_animation = Resources.Animation.GHOSTDEAD.getAnimation();
    }

    @Override
    protected boolean condition(int x, int y) {
        int x_  = (int) ((player.location_x + 24) / Resources.BLOCK_SIZE);
        int y_  = (int) ((player.location_y + 48) / Resources.BLOCK_SIZE);
        return x == x_ && y == y_;
    }

    @Override
    protected int update(Player player) {
        this.player = player;
        if (is_dead) {
            if (frame >= 20) {
                int x  = (int) ((location_x + 24) / Resources.BLOCK_SIZE);
                int y  = (int) ((location_y + 48) / Resources.BLOCK_SIZE);
                room.makeCoin(x, y);
                return -1;
            }
        } else {
            burn();
            if (!up && !right && !down && !left)
            findTheWay(room.getType() == Room.DUNGEON);
            move();
        }
        return 0;
    }
}
