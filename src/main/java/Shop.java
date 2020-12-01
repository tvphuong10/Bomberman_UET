import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Shop extends Object {
    private BufferedImage hub_image;
    private Player player;
    private int x;
    private int y;
    ArrayList<Button> buttons;

    public Shop(int x, int y, Player player) {
        this.player = player;
        this.x = x;
        this.y = y;
        buttons = new ArrayList<>();
        hub_image = Resources.Images.SHOP.getImage();
        for (int i = 0; i < 6; i++) {
            buttons.add(new Button(x + 350, y + 110 + i * 50, i, player));
        }
    }

    public void click(int x, int y) {
        for (Button b : buttons) {
            b.click(x, y);
        }
    }

    public void draw(Graphics g) {
        g.drawImage(hub_image ,x ,y , null);
        for (Button b : buttons) {
            b.draw(g);
        }
    }
}

class Button {
    int x;
    int y;
    int from;
    int to;
    int width;
    int height;
    boolean select;
    int gold;
    int type;
    Player player;

    BufferedImage button_image;
    BufferedImage button_image_select;

    public Button(int x, int y, int type, Player player) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.player = player;
        Random ran = new Random();
        from = ran.nextInt(3) + 1;
        int r = ran.nextInt(6);
        to = from + ran.nextInt(3) + 1;
        if (type == 1 && to % 2 != 0 ) to++;
        if (type == 2 && from % 2 != 0) from++;
        if (type == 5 && from % 2 != 0) from++;
        if (type > 2) {
            to = to % 2 + 1;
            gold = ran.nextInt((to * 4) + 10);
        }else {
            gold = ran.nextInt((to - from + 1) * 4) + 10;
        }
        width = 98;
        height = 46;
        button_image = Resources.Images.BUTTON.getImage();
        button_image_select = Resources.Images.BUTTONS.getImage();
    }

    public void click (int x_, int y_) {
        if (x_ > x && x_ < x + width && y_ > y && y_ < y + height) {
            select = true;
            if (player.gold >= gold ) {
                if (type == 0 && player.power > from) {
                    player.gold -= gold;
                    player.power -= from;
                    player.bombNumber += to;
                } else if (type == 1 && player.bombNumber > from) {
                    player.gold -= gold;
                    player.bombNumber -= from;
                    player.speed += to;
                } else if (type == 2 && player.speed > from) {
                    player.gold -= gold;
                    player.speed -= from;
                    player.power += to;
                } else if (type == 3 && player.power > from) {
                    player.gold -= gold;
                    player.power -= from;
                    player.life += to;
                } else if (type == 4 && player.bombNumber > from) {
                    player.gold -= gold;
                    player.bombNumber -= from;
                    player.life += to;
                } else if (type == 5 && player.speed > from) {
                    player.gold -= gold;
                    player.speed -= from;
                    player.life += to;
                }
                else {
                    select = false;
                }
            }  else {
                select = false;
            }
        }
    }

    public boolean isSelect() {
        return select;
    }

    public void draw(Graphics g) {
        if (select)  {
            g.setColor(Color.gray);
            g.drawImage(button_image_select, x, y, width, height, null);
            g.drawString(from + "                             " + to + "                    " + gold, x - 280, y + 32);
        }
        else {
            g.setColor(Color.white);
            g.drawImage(button_image, x, y, width, height, null);
            g.drawString(from + "                             " + to + "                    " + gold, x - 280, y + 32);
        }
    }
}
