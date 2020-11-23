import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Queue;

public abstract class Character extends Object {
    boolean up;
    boolean down;
    boolean left;
    boolean right;
    boolean is_dead;
    boolean flip;

    int speed;
    int x_room;
    int y_room;

    Room room;
    BufferedImage image;
    BufferedImage[] animation;
    BufferedImage[] dead_animation;
    BufferedImage[] run_animation;
    private Queue<node> que;

    public int getXRoom() {return x_room;}
    public int getYRoom() {return y_room;}

    public void Init(Room room, BufferedImage[] animation, BufferedImage[] run, BufferedImage[] dead) {
        this.room = room;
        this.animation = animation;
        this.run_animation = run;
        this.dead_animation = dead;
        this.image = this.animation[0];
        up = false;
        right = false;
        down = false;
        left = false;
        is_dead = false;
        flip = false;
        speed = 4;
        x_room = 0;
        y_room = 0;
    }

    public void move () {
        int x = x_room * Resources.BLOCK_SIZE + 25;
        int y = y_room * Resources.BLOCK_SIZE + 25;
        this.left = false;
        this.right = false;
        this.up = false;
        this.down = false;
        if (location_x + 26 > x + this.speed) this.left = true;
        else if (location_x + 26 < x - this.speed) this.right = true;
        else location_x = x - 26;
        if (location_y + 50 < y - this.speed) this.down = true;
        else if (location_y + 50 > y + this.speed) this.up = true;
        else location_y = y - 50;
    }

    public void findTheWay(int x, int y, boolean careful) {
        if (careful) {
            x_room  = (int) ((location_x + 24) / Resources.BLOCK_SIZE);
            y_room  = (int) ((location_y + 48) / Resources.BLOCK_SIZE);
        }
        que = new LinkedList<>();
        if (!blocked(x_room, y_room - 1))    que.add(new node(x_room, y_room - 1, 0, 9));
        if (!blocked(x_room, y_room + 1))    que.add(new node(x_room, y_room + 1, 2, 9));
        if (!blocked(x_room + 1, y_room))    que.add(new node(x_room + 1, y_room, 1, 9));
        if (!blocked(x_room - 1, y_room))    que.add(new node(x_room - 1, y_room, 3, 9));
        if (que.size() == 0) return;
        while (!que.isEmpty()) {
            node n = que.poll();
            if (n.count == 0) {
                return;
            }
            if (n.x == x && n.y == y) {
                if (n.z == 0) y_room--;
                if (n.z == 1) x_room++;
                if (n.z == 2) y_room++;
                if (n.z == 3) x_room--;
                return;
            }
            if (!blocked(n.x, n.y - 1))    que.add(new node(n.x, n.y - 1, n.z, n.count - 1));
            if (!blocked(n.x, n.y + 1))    que.add(new node(n.x, n.y + 1, n.z, n.count - 1));
            if (!blocked(n.x + 1, n.y))    que.add(new node(n.x + 1, n.y, n.z, n.count - 1));
            if (!blocked(n.x - 1, n.y))    que.add(new node(n.x - 1, n.y, n.z, n.count - 1));
        }
    }

    private boolean blocked(int x, int y) {
        if (x == x_room && y == y_room) return true;
        if (room.get(x, y).charAt(0) == 'F') return true;
        return room.blocked(x, y);
    }
}
