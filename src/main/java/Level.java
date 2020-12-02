import java.awt.*;
import java.util.Random;

public class Level extends Object {
    public final int LEVEL_SIZE = 5; // kich co level.
    private int i;
    private Room[][] rooms;
    private Player player;
    private int level;
    private boolean pause;
    private boolean shopping;
    private Shop shop;

    public Player getPlayer() {return player;}

    public Shop getHub() {
        return shop;
    }
    public void setPause(boolean b) {this.pause = b;}
    public boolean isPause() {return pause;}
    public boolean isShopping() {return shopping;}

    /**
     * khoi tao level, chon rooms[4][2] la phong khoi dau.
     */
    public Level() {
        pause = true;
        i = 0;
        level = 1;
        rooms = new Room[LEVEL_SIZE][LEVEL_SIZE];
        rooms[4][2] = new Room(Room.START, level);

        buildLevel();
        player = new Player();
        player.Init(rooms[4][2],Resources.Animation.TEEMO.getAnimation(),
                Resources.Animation.RUN.getAnimation(),
                Resources.Animation.TEEMODEAD.getAnimation());
        player.setRoom(4,2,rooms[4][2]);
        shop = new Shop(77, 60, player);
    }

    public void pause() {
        if (pause) pause = false;
        else pause = true;
    }

    public void shoping() {
        if (shopping) shopping = false;
        else shopping = true;
    }

    /**
     * xay level.
     */

    public void buildLevel() {
        Random ran = new Random();
        boolean up = false;
        boolean right = false;
        boolean down = true;
        boolean left = true;
        // chon phong END sau do tao cac phong noi phong END den START.
        int x = ran.nextInt(5);
        int y = ran.nextInt(2);
        rooms[y][x] = new Room(Room.END, level);
        while (y != 4 || x != 2) {
            int r = ran.nextInt(2);
            if (r == 0 && y < 4) y++;
            if (r == 1 && x < 2) x++;
            if (r == 1 && x > 2) x--;
            rooms[y][x] = new Room(Room.DUNGEON, level);
        }
        // xay SHOP va cac DUNGEON xung quanh.
        buildShop();
        buildDungeon();
        // khoi tao phong START.
        rooms[4][2] = new Room(Room.START, level);
        rooms[4][2].show = true;
        // neu co phong xung quanh thi xay cua den phong do.
        for (int i = 0; i < LEVEL_SIZE; i++) {
            for (int j = 0; j < LEVEL_SIZE; j++) {
                if (rooms[i][j] != null) {
                    up = false;
                    right = false;
                    down = false;
                    left = false;
                    if (i - 1 >= 0 && rooms[i - 1][j] != null) up = true;
                    if (i + 1 < LEVEL_SIZE && rooms[i + 1][j] != null) down = true;
                    if (j - 1 >= 0 && rooms[i][j - 1] != null) left = true;
                    if (j + 1 < LEVEL_SIZE && rooms[i][j + 1] != null) right = true;
                    rooms[i][j].buildRoom(up,right,down,left);
                }
            }
        }
        shop = new Shop(77, 60, player);
    }

    /**
     * xay 1 SHOP.
     */
    public void buildShop() {
        Random ran = new Random();
        int y = 2 + ran.nextInt(2);
        for (int x = 0; x < LEVEL_SIZE; x++) {
            if (rooms[y][x] != null) {
                if (x - 1 >= 0 && rooms[y][x - 1] == null) {
                    rooms[y][x - 1] = new Room(Room.SHOP, level);
                    return;
                }
                if (x + 1 < LEVEL_SIZE && rooms[y][x + 1] == null) {
                    rooms[y][x + 1] = new Room(Room.SHOP, level);
                    return;
                }
            }
        }
    }

    /**
     * xay DUNGEON ben canh nhung phong khac null.
     */
    public void buildDungeon() {
        Random ran = new Random();
        for (int i = 0; i < LEVEL_SIZE; i++) {
            for (int j = 0; j < LEVEL_SIZE; j++) {
                if (rooms[i][j] == null && ran.nextInt() % 3 == 0) {
                    if ((i + 1 < LEVEL_SIZE && rooms[i + 1][j] != null)
                    || (i - 1 >= 0 && rooms[i - 1][j] != null)
                    || (j + 1 < LEVEL_SIZE && rooms[i][j + 1] != null)
                    || (j - 1 >= 0 && rooms[i][j - 1] != null)) {
                        rooms[i][j] = new Room(Room.DUNGEON,level);
                    }
                }
            }
        }
    }

    /**
     * cap nhat cac phong va xu ly su kien giup nhan vat di qua lai cac phong.
     * @return 0 neu khong co gi say ra.
     */
    protected int update() {
        if (!pause) {
            i++;
            int u = player.update();
            if (u > 0) {
                int y = player.getLevel_y();
                int x = player.getLevel_x();
                if (u == 1) x--;
                else if (u == 2) y++;
                else if (u == 3) x++;
                else if (u == 4) y--;
                else if (u == 5) {
                    x = 4;
                    y = 2;
                    level++;
                    rooms = new Room[LEVEL_SIZE][LEVEL_SIZE];
                    rooms[4][2] = new Room(Room.START, level);
                    buildLevel();
                }
                player.setRoom(x, y, rooms[x][y]);
                rooms[x][y].show = true;
                player.enterRoom((u + 1) % 4 + 1);
                i = 0;
            } else if (u < 0) replay();
            if (i % 4 == 0 || u > 0) {
                for (int i = 0; i < LEVEL_SIZE; i++) {
                    for (int j = 0; j < LEVEL_SIZE; j++) {
                        if (rooms[i][j] != null) {
                            rooms[i][j].update(player);
                        }
                    }
                }
            }
        }
        return 0;
    }

    private void replay() {
        level = 1;
        rooms = new Room[LEVEL_SIZE][LEVEL_SIZE];
        rooms[4][2] = new Room(Room.START, level);
        buildLevel();
        player.Init(rooms[4][2],Resources.Animation.TEEMO.getAnimation(),
                Resources.Animation.RUN.getAnimation(),
                Resources.Animation.TEEMODEAD.getAnimation());
        player.setRoom(4,2,rooms[4][2]);
        rooms[4][2].show = true;
        shop = new Shop(77, 60, player);
    }

    protected void draw(Graphics g) {
        g.setFont(new Font("Arial",Font.BOLD, 20));
        g.setColor(Color.white);
        player.room.draw(g, player);
        int x = 525;
        if (player.x_room > 7 && player.y_room < 7) x = 5;
        g.drawImage(Resources.Images.BACKGROUND.getImage(), x , 5, null);
        g.drawImage(Resources.Images.COIN.getImage(), 535 - x , 5, null);
        g.drawImage(Resources.Images.LIFE.getImage(), 535 - x , 35, null);
        g.drawString(player.gold + "" , 570 - x, 33);
        g.drawString(player.life + "" , 570 - x, 63);
        g.drawString("level: " + level, 450, 33);

        for (int i = 0; i < LEVEL_SIZE; i++) {
            for (int j = 0; j < LEVEL_SIZE; j++) {
                if (rooms[j][i] != null && rooms[j][i].show)
                if (j == player.getLevel_x() && i == player.level_y) {
                    g.drawImage(Resources.Images.MINIMAP2.getImage(), x + i * 23 + 2, j * 23, null);
                } else {
                    g.drawImage(Resources.Images.MINIMAP.getImage(), x + i * 23 + 2, j * 23, null);
                }
            }
        }

        if (player.room.get(6, 6).charAt(0) != 'K') {
            shopping = false;
        } else if (shopping) {
            shop.draw(g);
        }
        if (pause) {
            g.drawImage(Resources.Images.PAUSE.getImage(), 0, 0, null);
            drawHub(g, 420, 250);
        }
    }

    /**
     * ve bang thong tin
     * @param g do hoa
     */

    private void drawHub(Graphics g, int x, int y) {
        g.setColor(Color.white);
        g.drawString("Level: " + level , x, y);
        g.setColor(Color.ORANGE);
        g.drawString("Gold: " + player.gold , x, y + 40);
        g.setColor(Color.white);
        g.drawString("Life: " + player.life , x, y + 80);
        g.drawString("Bomb: " + player.bombNumber , x, y + 120);
        g.drawString("Pow: " + player.power , x, y + 160);
        g.drawString("Speed: " + player.speed , x, y + 200);
    }
}
