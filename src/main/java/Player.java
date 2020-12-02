import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Character {
    int immortal_timer;
    int level_x;
    int level_y;
    int last_bomb_x;
    int last_bomb_y;
    int frame;

    String name;
    char id;
    int gold;
    int life;
    int bombNumber;
    int power;

    public int getY() {return (int) ((location_y + 55) / Resources.BLOCK_SIZE);}
    public Room getRoom() {return room;}
    public int getLevel_x() {return level_x;}
    public int getLevel_y() {return level_y;}
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
        last_bomb_x = -1;
        level_y = -1;
        this.gold = 0;
        this.immortal_timer = 0;
        this.speed = 4;
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
        last_bomb_x  = (int) ((location_x + 24) / Resources.BLOCK_SIZE);
        last_bomb_y  = (int) ((location_y + 48) / Resources.BLOCK_SIZE);
        room.putBomb(last_bomb_x, last_bomb_y, bombNumber , power, id);
    }

    public void upPressed() {up = true;}
    public void downPressed() {down = true;}
    public void rightPressed() {
        right = true;
        flip = false;
    }
    public void leftPressed() {
        left = true;
        flip = true;
    }

    public void upReleased() {up = false;}
    public void downReleased() {down = false;}
    public void rightReleased() {right = false;}
    public void leftReleased() {left = false;}

    private void pickItem() {
        int x  = (int) ((location_x + 24) / Resources.BLOCK_SIZE);
        int y  = (int) ((location_y + 48) / Resources.BLOCK_SIZE);
        gold += room.getCoin(x, y);
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
    }

    private void dead() {
        immortal_timer = 46;
        frame = 0;
        life--;
    }

    protected int update() {
        if (immortal_timer > 0) immortal_timer--;
        if (life == 0 && frame >= 11) {
            return -1;
        } else {
            if (immortal_timer == 0) {
                pickItem();
                if (room.isMeetEnemy(x_room, y_room)) dead();
            }

            if (up)     this.location_y -= this.speed;
            if (down)   this.location_y += this.speed;
            if (right)  this.location_x += this.speed;
            if (left)   this.location_x -= this.speed;
            int x1 = (int) ((location_x + 10)  / Resources.BLOCK_SIZE); // 4 điểm tạo nên 1 hình chữ nhật
            int x2 = (int) ((location_x + 40) / Resources.BLOCK_SIZE); // hình chữ nhật đó là cơ thể vật lý của nhân vật
            int y1 = (int) ((location_y + 40) / Resources.BLOCK_SIZE); // 4 điểm được chuyển từ tọa độ trên màn hình location thành tọa độ trên map
            int y2 = (int) ((location_y + 60) / Resources.BLOCK_SIZE); // kiểm tra va chạm bằng cách kiểm tra 4 điểm này có vào ô bị chặn ko

            if (x1 < 0) {
                location_x = room.getMapWeigh() * Resources.BLOCK_SIZE - 40;
                return 4;
            }
            if (y2 >= room.getMapHeight()) {
                location_y = -10;
                return 3;
            }
            if (x2 >= room.getMapWeigh()) {
                location_x = 0;
                return 2;
            }
            if (y1 < 0) {
                location_y = room.getMapHeight() * Resources.BLOCK_SIZE - 65;
                return 1;
            }
            collisionTest(x1, x2, y1, y2);
            x_room  = (int) ((location_x + 24) / Resources.BLOCK_SIZE);
            y_room  = (int) ((location_y + 48) / Resources.BLOCK_SIZE);
            if (room.get(x_room, y_room).charAt(0) == '+') return 5;
        }
        return 0;
    }

    private void collisionTest(int x1, int x2, int y1, int y2) {
        int a = 0;
        if (blocked(x1, y1)) {
            this.location_y += this.speed / 2;
            this.location_x += this.speed / 2;
            a++;
        }
        if (blocked(x2, y1)) {
            this.location_y += this.speed / 2;
            this.location_x -= this.speed / 2;
            a--;
        }
        if (blocked(x1, y2)) {
            this.location_y -= this.speed / 2;
            this.location_x += this.speed / 2;
            a--;
        }
        if (blocked(x2, y2)) {
            this.location_y -= this.speed / 2;
            this.location_x -= this.speed / 2;
            a++;
        }

        if (a == 2 || a == -2) {
            if (up)     this.location_y += this.speed;
            if (down)   this.location_y -= this.speed;
            if (right)  this.location_x -= this.speed;
            if (left)   this.location_x += this.speed;
        }

        if (a == 1 || a == -1) {
            if (up)     this.location_y += this.speed / 2;
            if (down)   this.location_y -= this.speed / 2;
            if (right)  this.location_x -= this.speed / 2;
            if (left)   this.location_x += this.speed / 2;
        }

        if (((x1 != last_bomb_x) && (x2 != last_bomb_x)) || ((y1 != last_bomb_y) && (y2 != last_bomb_y))) {
            this.last_bomb_x = -1;
            this.last_bomb_y = -1;
        }
    }

    private boolean blocked(int x, int y) {
        if (x == last_bomb_x && y == last_bomb_y) return false;
        return room.blocked(x, y);
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
            if (flip)   g.drawImage(this.run_animation[frame / 4], location_x + width, location_y + 15, -width, height, null);
            else        g.drawImage(this.run_animation[frame / 4], location_x , location_y + 15, width, height, null);
        } else {
            if (flip)   g.drawImage(this.run_animation[1], location_x + width, location_y + 15, -width, height, null);
            else        g.drawImage(this.run_animation[1], location_x , location_y + 15, width, height, null);
        }
    }
}
