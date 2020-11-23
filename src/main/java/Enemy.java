import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Enemy extends Character {
    int frame;

    public int getY() {return (int) ((location_y + 50) / Resources.BLOCK_SIZE);}
    public void dead() {
        this.is_dead = true;
        frame = 0;
    }

    Enemy (int x, int y, Room room) {
        super();
        super.Init(room, Resources.Animation.BAT.getAnimation(),
                Resources.Animation.RUN.getAnimation(),
                Resources.Animation.BATDEAD.getAnimation());
        this.is_dead = false;
        this.height = 60;
        this.width = 52;
        this.x_room = x;
        this.y_room = y;
        this.location_x = x * Resources.BLOCK_SIZE;
        this.location_y = y * Resources.BLOCK_SIZE;
        frame = 0;
        this.speed = 10;
    }

    private void burn() {
        int x  = (int) ((location_x + 24) / Resources.BLOCK_SIZE);
        int y  = (int) ((location_y + 48) / Resources.BLOCK_SIZE);
        if(room.get(x, y).charAt(0) == 'F') {
            is_dead = true;
        }
    }

    @Override
    protected int update() {
        if (is_dead) {
            if (frame >= 11) {
                return -1;
            }
        } else {
            burn();
            if (!up && !down && !right && !left) {
                Random ran = new Random();
                int r = ran.nextInt(4);
                if (r == 0 && room.get(x_room - 1, y_room).charAt(0) == ' ') x_room--;
                if (r == 1 && room.get(x_room, y_room + 1).charAt(0) == ' ') y_room++;
                if (r == 2 && room.get(x_room + 1, y_room).charAt(0) == ' ') x_room++;
                if (r == 3 && room.get(x_room, y_room - 1).charAt(0) == ' ') y_room--;
            }
            move();
            if (up)     this.location_y -= this.speed;
            if (down)   this.location_y += this.speed;
            if (right)  this.location_x += this.speed;
            if (left)   this.location_x -= this.speed;
        }
        return 0;
    }

    protected void draw(Graphics g) {
        frame ++;
        if (is_dead) {
            if (frame >= 12) frame --;
            else g.drawImage(this.dead_animation[frame / 3] ,location_x, location_y ,width ,height ,null);
        } else {
            if (frame >= 12) frame = 0;
            if (flip)   g.drawImage(this.animation[frame / 3] ,location_x + width ,location_y ,-width ,height ,null);
            else        g.drawImage(this.animation[frame / 3] ,location_x ,location_y ,width ,height ,null);
        }
    }
}
