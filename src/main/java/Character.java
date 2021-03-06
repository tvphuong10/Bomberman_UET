import java.awt.image.BufferedImage;
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

    /**
     * thiet lap up down left right sau do di chuyen.
     */
    public void move () {
        int x = x_room * Resources.BLOCK_SIZE + 25;
        int y = y_room * Resources.BLOCK_SIZE + 25;
        this.left = false;
        this.right = false;
        this.up = false;
        this.down = false;

        if (y_room == 0 && room.main_gate == 1) this.up = true;
        else if (x_room == 0 && room.main_gate == 4) this.left = true;
        else if (y_room == 12 && room.main_gate == 3) this.down = true;
        else if (x_room == 12 && room.main_gate == 2) this.right = true;
        else {
            if (location_x + 26 > x + this.speed) this.left = true;
            else if (location_x + 26 < x - this.speed) this.right = true;
            else location_x = x - 26;
            if (location_y + 50 < y - this.speed) this.down = true;
            else if (location_y + 50 > y + this.speed) this.up = true;
            else location_y = y - 50;
        }

        if (up)     this.location_y -= this.speed;
        if (down)   this.location_y += this.speed;
        if (right) {this.location_x += this.speed; flip = false; }
        if (left)  {this.location_x -= this.speed; flip = true; }
    }

    /**
     * dieu kien de findTheWay() hoat dong (chi can overwrite lai ham nay).
     * @param x toa do x o dang xet.
     * @param y toa do y o dang xet.
     * @return true neu o thoa man.
     */
    protected boolean condition(int x, int y) {
        return false;
    }

    /**
     * tim duong den noi thua man dieu kien ham condition().
     * @param range pham vi.
     */

    public void findTheWay(int range) {
        boolean[][] arr = new boolean[13][13];
        que = new LinkedList<>();
        if (!blocked(x_room, y_room - 1, arr))    que.add(new node(x_room, y_room - 1, 0, range));
        if (!blocked(x_room, y_room + 1, arr))    que.add(new node(x_room, y_room + 1, 2, range));
        if (!blocked(x_room + 1, y_room, arr))    que.add(new node(x_room + 1, y_room, 1, range));
        if (!blocked(x_room - 1, y_room, arr))    que.add(new node(x_room - 1, y_room, 3, range));
        if (que.size() == 0) return;
        while (!que.isEmpty()) {
            node n = que.poll();
            if (n.count == 0) {
                return;
            }
            if (condition(n.x, n.y)) {
                //System.out.print(n.z);
                if (n.z == 0) y_room--;
                if (n.z == 1) x_room++;
                if (n.z == 2) y_room++;
                if (n.z == 3) x_room--;
                return;
            }
            if (!blocked(n.x, n.y - 1, arr))    que.add(new node(n.x, n.y - 1, n.z, n.count - 1));
            if (!blocked(n.x, n.y + 1, arr))    que.add(new node(n.x, n.y + 1, n.z, n.count - 1));
            if (!blocked(n.x + 1, n.y, arr))    que.add(new node(n.x + 1, n.y, n.z, n.count - 1));
            if (!blocked(n.x - 1, n.y, arr))    que.add(new node(n.x - 1, n.y, n.z, n.count - 1));
            arr[n.x][n.y] = true;
        }
    }

    /**
     * bi chan.
     * @param x toa do x.
     * @param y toa do y.
     * @return true neu bi chan.
     */

    protected boolean blocked(int x, int y, boolean[][] arr) {
        if (x == x_room && y == y_room) return true;
        if (x >= 0 && y >= 0 && x < 13 && y < 13) {
            if (arr[x][y]) return true;
        }
        if (room.get(x, y).charAt(0) == 'F') return true;
        return room.blocked(x, y);
    }

}

/**
 * node de phuc vu ham findTheWay().
 */

class node {
    public int x;
    public int y;
    public int z;
    public int count;

    node(int x, int y, int z, int count) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.count = count;
    }
}
