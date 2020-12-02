import java.util.Random;

public class Spider extends Enemy {
    protected  int time;
    Spider(int x, int y, Room room, int level) {
        super(x, y, room, level);
        this.speed = 15 * level;
        this.animation = Resources.Animation.LEESIN.getAnimation();
        this.dead_animation = Resources.Animation.LEESINDEAD.getAnimation();
        time = 0;

    }
    protected int update(Player player) {

        if (is_dead) {
            if (frame >= 20) {
                int x  = (int) ((location_x + 24) / Resources.BLOCK_SIZE);
                int y  = (int) ((location_y + 48) / Resources.BLOCK_SIZE);
                room.makeCoin(x, y); // chet roi tien
                return -1;
            }
        } else {
            burn();
            if (!up && !down && !right && !left) {

                Random ran = new Random();
                int r = ran.nextInt(4);
                if (r == 0 && (room.get(x_room - 1, y_room).charAt(0) == ' '  ))
                {   time ++;
                    x_room--;

                    if(time == 7) {
                        room.putPoison(y_room, x_room+1);
                        time = 0;
                    }
                }
                if (r == 1 && (room.get(x_room, y_room + 1).charAt(0) == ' '  ))
                {   time ++;
                    y_room++;
                    if(time == 7 )
                    {
                        room.putPoison(y_room - 1, x_room);
                        time = 0;
                    }
                }
                if (r == 2 && (room.get(x_room + 1, y_room).charAt(0) == ' '  ))
                {   time ++;
                    x_room++;
                    if(time == 7)
                    {
                        room.putPoison(y_room, x_room - 1);
                        time = 0;
                    }
                }
                if (r == 3 && (room.get(x_room, y_room - 1).charAt(0) == ' '  ))
                {   time ++;
                    y_room--;
                    if(time == 7)
                    {
                        room.putPoison(y_room + 1, x_room);
                        time = 0;
                    }
                }
            }
            move();
        }
        return 0;
    }
}
