import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Character {
    boolean in_room;
    int immortal_timer;
    int level_x;
    int level_y;
    int mouse_x;
    int mouse_y;
    int frame;

    String name;
    char id;
    int life;
    int bombNumber;
    int power;

    public int getY() {return (int) ((location_y + 55) / Resources.BLOCK_SIZE);}
    public Room getRoom() {return room;}
    public int getLevel_x() {return level_x;}
    public int getLevel_y() {return level_y;}
    public int getMouse_x() {return mouse_x;}
    public int getMouse_y() {return mouse_y;}

    public void setXY(int x, int y) {
        int x_ = (int) x / Resources.BLOCK_SIZE;
        int y_ = (int) y / Resources.BLOCK_SIZE;
        if (!room.blocked(x_, y_)) {
            mouse_x = x_;
            mouse_y = y_;
        }
    }
    public void setID(int i) {this.id = (char)i;}
    public void setRoom(int x, int y, Room room) {
        level_x = x;
        level_y = y;
        this.room = room;
    }

    public void Init(Room room, BufferedImage[] animation, BufferedImage[] run, BufferedImage[] dead) {
        super.Init(room, animation, run, dead);
        level_x = 0;
        level_y = 0;
        mouse_x = 0;
        mouse_y = 0;
        this.immortal_timer = 0;
        this.height = 60;
        this.width = 52;
        this.frame = 0;
        this.life = 2;
        this.power = 1;
        this.bombNumber = 1;
        this.setID(0);
        this.name = "Teemo";
        this.location_x = 6 * Resources.BLOCK_SIZE;
        this.location_y = 5 * Resources.BLOCK_SIZE;
        this.x_room = 6;
        this.y_room = 5;
    }

    public void putBomb() {
        System.out.println("pip");
        int x  = (int) ((location_x + 24) / Resources.BLOCK_SIZE);
        int y  = (int) ((location_y + 48) / Resources.BLOCK_SIZE);
        room.putBomb(x, y, bombNumber , power, id);
    }

    private void pickItem() {
        int x  = (int) ((location_x + 24) / Resources.BLOCK_SIZE);
        int y  = (int) ((location_y + 48) / Resources.BLOCK_SIZE);
        switch (room.getItem(x, y)) {
            case 0 -> {
                dead();
            }
            case 1 -> power ++;
            case 2 -> speed += 2;
            case 3 -> bombNumber ++;
        }
        if (power > 23) power--;
    }

    public void enterRoom(int gate) {
        boolean first_time = false;
        for (int i = 0; i < room.getMapHeight(); i++) {
            for (int j = 0; j < room.getMapWeigh(); j++) {
                if (room.get(j,i).charAt(0) == gate + '0') {
                    if (first_time) {
                        x_room = j;
                        y_room = i;
                        location_x = x_room * Resources.BLOCK_SIZE;
                        location_y = y_room * Resources.BLOCK_SIZE;
                    } else {
                        first_time = true;
                    }
                }
            }
        }
    }

    private void dead() {
        immortal_timer = 46;
        frame = 0;
        life--;
    }

    @Override
    protected int update() {
        if (immortal_timer > 0) immortal_timer--;
        if (life == 0) {
            if (frame >= 11) {
                return -1;
            }
        } else {
            if (immortal_timer == 0) {
                pickItem();
                if (room.isMeetEnemy(x_room, y_room)) {
                    dead();
                }
            }
            if (frame % 4 == 0)
            findTheWay(mouse_x, mouse_y, room.getType() == Room.DUNGEON);
            move();
            if (up)     this.location_y -= this.speed;
            if (down)   this.location_y += this.speed;
            if (right)  {
                this.location_x += this.speed;
                flip = false;
            }
            if (left)   {
                this.location_x -= this.speed;
                flip = true;
            }
            int x  = (int) ((location_x + 24) / Resources.BLOCK_SIZE);
            int y  = (int) ((location_y + 48) / Resources.BLOCK_SIZE);
            if (room.isExit(x,y) != -1 ) {
                if (in_room) {
                    in_room = false;
                    return room.isExit(x,y);
                }
            } else {
                in_room  = true;
            }
        }
        return 0;
    }

    protected void draw(Graphics g) {
        frame ++;
        if (life == 0) {
            if (frame >= 12) frame --;
            else g.drawImage(this.dead_animation[frame / 3] ,location_x, location_y - 200 + height ,50 ,200 ,null);
        } else {
            if (immortal_timer > 0 && frame % 6 > 2) return;
            if (frame >= 12) frame = 0;
            g.drawString(name, location_x ,location_y - 2);
            drawLeg(g);
            if (flip)   g.drawImage(this.animation[frame / 3] ,location_x + width ,location_y ,-width ,height ,null);
            else        g.drawImage(this.animation[frame / 3] ,location_x ,location_y ,width ,height ,null);
        }
    }

    private void drawLeg(Graphics g) {
        if(up || down || right || left) {
            if (flip)   g.drawImage(this.run_animation[frame / 4], location_x + width, location_y + 2, -width, height, null);
            else        g.drawImage(this.run_animation[frame / 4], location_x , location_y + 2, width, height, null);
        } else {
            if (flip)   g.drawImage(this.run_animation[1], location_x + width, location_y + 2, -width, height, null);
            else        g.drawImage(this.run_animation[1], location_x , location_y + 2, width, height, null);
        }
    }
}


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
