import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Room extends Object {
    public static final int MAP_SIZE = 13;

    public static final int START = 0;
    public static final int DUNGEON = 1;
    public static final int SHOP = 2;
    public static final int END = 3;

    private String[][] map;
    private int[][] map_status;
    private int map_height;
    private int map_weigh;
    private int type;
    private int level;
    protected boolean show;

    private ArrayList<Enemy> enemies = new ArrayList<>();

    public int getType() {return type;}
    public int getMapHeight() {return map_height;}
    public int getMapWeigh() {return map_weigh;}

    public Room(int type, int level) {
        this.type = type;
        this.level = level;
        show = false;
        String[][] s;
        if (type == 0) {
            s = new String[][]{
                    {"#", "#", "#", "#", "#", "1", "1", "1", "#", "#", "#", "#", "#"},
                    {"#", " ", " ", " ", " ", " ", "/", " ", " ", " ", " ", " ", "#"},
                    {"#", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "#"},
                    {"#", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "#"},
                    {"#", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "#"},
                    {"4", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "2"},
                    {"4", "/", " ", " ", " ", " ", " ", " ", " ", " ", " ", "/", "2"},
                    {"4", " ", " ", " ", " ", "#1", " ", "#1", " ", " ", " ", " ", "2"},
                    {"#", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "#"},
                    {"#", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "#"},
                    {"#", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "#"},
                    {"#", " ", " ", " ", " ", " ", "/", " ", " ", " ", " ", " ", "#"},
                    {"#", "#", "#", "#", "#", "3", "3", "3", "#", "#", "#", "#", "#"}
            };
        } else if (type == 1) {
            Random ran = new Random();
            int r = ran.nextInt(4);
            if (r == 0) {
                s = new String[][]{
                        {"#", "#", "#", "#", "#", "1", "1", "1", "#", "#", "#", "#", "#"},
                        {"#", "B", " ", " ", " ", " ", "/", " ", " ", " ", " ", "B", "#"},
                        {"#", " ", "W", " ", "W", " ", "W", " ", "W", " ", "W", " ", "#"},
                        {"#", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "#"},
                        {"#", " ", "W", " ", "W", " ", "W", " ", "W", " ", "W", " ", "#"},
                        {"4", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "2"},
                        {"4", "/", "W", " ", "W", "G", "#1", "G", "W", " ", "W", "/", "2"},
                        {"4", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "2"},
                        {"#", " ", "W", " ", "W", " ", "W", " ", "W", " ", "W", " ", "#"},
                        {"#", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "#"},
                        {"#", "B", "W", " ", "W", " ", "W", " ", "W", " ", "W", "B", "#"},
                        {"#", " ", " ", " ", " ", " ", "/", " ", " ", " ", " ", " ", "#"},
                        {"#", "#", "#", "#", "#", "3", "3", "3", "#", "#", "#", "#", "#"}
                };
            } else if (r == 1) {
                s = new String[][]{
                        {"#", "#", "#", "#", "#", "1", "1", "1", "#", "#", "#", "#", "#"},
                        {"#", "B", " ", "G", "#", " ", "/", " ", "#", "B", " ", "B", "#"},
                        {"#", " ", " ", " ", "#", " ", "W", " ", "#", " ", " ", " ", "#"},
                        {"#", " ", " ", " ", "#", " ", " ", " ", "#", " ", " ", " ", "#"},
                        {"#", "#", "#", " ", "#1", " ", "W", " ", "#1", " ", "#", "#", "#"},
                        {"4", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "2"},
                        {"4", "/", "W", " ", "W", "B", "W", "B", "W", " ", "W", "/", "2"},
                        {"4", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "2"},
                        {"#", "#", "#", " ", "#1", " ", "W", " ", "#1", " ", "#", "#", "#"},
                        {"#", " ", " ", " ", "#", " ", " ", " ", "#", " ", " ", " ", "#"},
                        {"#", "B", " ", " ", "#", " ", "W", " ", "#", " ", " ", "B", "#"},
                        {"#", " ", " ", "B", "#", " ", "/", " ", "#", "G", " ", " ", "#"},
                        {"#", "#", "#", "#", "#", "3", "3", "3", "#", "#", "#", "#", "#"}
                };
            } else if ( r == 2) {
                s = new String[][]{
                        {"#", "#", "#", "#", "#", "1", "1", "1", "#", "#", "#", "#", "#"},
                        {"#", " ", " ", " ", " ", " ", "/", " ", " ", " ", " ", " ", "#"},
                        {"#", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "#"},
                        {"#", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "#"},
                        {"#", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "#"},
                        {"4", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "2"},
                        {"4", "/", " ", " ", " ", " ", "#1", " ", " ", " ", " ", "/", "2"},
                        {"4", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "2"},
                        {"#", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "#"},
                        {"#", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "#"},
                        {"#", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "#"},
                        {"#", " ", " ", " ", " ", " ", "/", " ", " ", " ", " ", " ", "#"},
                        {"#", "#", "#", "#", "#", "3", "3", "3", "#", "#", "#", "#", "#"}
                };
            } else {
                s = new String[][]{
                        {"#", "#", "#", "#", "#", "1", "1", "1", "#", "#", "#", "#", "#"},
                        {"#", " ", " ", " ", " ", " ", "/", " ", " ", " ", " ", " ", "#"},
                        {"#", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "#"},
                        {"#", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "#"},
                        {"#", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "#"},
                        {"4", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "2"},
                        {"4", "/", " ", " ", " ", " ", "#1", " ", " ", " ", " ", "/", "2"},
                        {"4", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "2"},
                        {"#", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "#"},
                        {"#", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "#"},
                        {"#", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "#"},
                        {"#", " ", " ", " ", " ", " ", "/", " ", " ", " ", " ", " ", "#"},
                        {"#", "#", "#", "#", "#", "3", "3", "3", "#", "#", "#", "#", "#"}
                };
            }
        } else if (type == 2) {
            s = new String[][]{
                    {"#", "#", "#", "#", "#", "1", "1", "1", "#", "#", "#", "#", "#"},
                    {"#", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "#"},
                    {"#", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "#"},
                    {"#", " ", " ", " ", " ", "W", " ", "W", " ", " ", " ", " ", "#"},
                    {"#", " ", " ", " ", " ", "W", "W", "W", " ", " ", " ", " ", "#"},
                    {"4", " ", " ", " ", " ", "W", "W", "W", " ", " ", " ", " ", "2"},
                    {"4", " ", " ", " ", " ", " ", "W", " ", " ", " ", " ", " ", "2"},
                    {"4", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "2"},
                    {"#", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "#"},
                    {"#", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "#"},
                    {"#", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "#"},
                    {"#", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "#"},
                    {"#", "#", "#", "#", "#", "3", "3", "3", "#", "#", "#", "#", "#"}
            };
        } else {
            s = new String[][]{
                    {"#", "#", "#", "#", "#", "1", "1", "1", "#", "#", "#", "#", "#"},
                    {"#", " ", " ", " ", " ", " ", "/", " ", " ", " ", " ", " ", "#"},
                    {"#", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "#"},
                    {"#", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "#"},
                    {"#", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "#"},
                    {"4", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "2"},
                    {"4", "/", " ", " ", " ", " ", "+", " ", " ", " ", " ", "/", "2"},
                    {"4", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "2"},
                    {"#", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "#"},
                    {"#", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "#"},
                    {"#", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "#"},
                    {"#", " ", " ", " ", " ", " ", "/", " ", " ", " ", " ", " ", "#"},
                    {"#", "#", "#", "#", "#", "3", "3", "3", "#", "#", "#", "#", "#"}
            };
        }
        map = s;
        map_height = map.length;
        map_weigh = map[0].length;
        map_status = new int[map_height][map_weigh];
        this.location_x = 0;
        this.location_y = 0;
    }

    /**
     * ham kiem tra nguoi choi di ra ngoai room.
     * @param x
     * @param y
     * @return 1-len 2-phai 3-xuong 4-trai -1-chua ra ngoai
     */

    public int isExit(int x, int y) {
        if (map[y][x].charAt(0) == '1') return 1;
        else if (map[y][x].charAt(0) == '2') return 2;
        else if (map[y][x].charAt(0) == '3') return 3;
        else if (map[y][x].charAt(0) == '4') return 4;
        else return -1;
    }

    /**
     * xay phong
     * @param up co phong o tren khong
     * @param right co phong ben phai khong
     * @param down co phong o duoi ko
     * @param left co phong ben trai ko
     */
    public void buildRoom(boolean up, boolean right, boolean down, boolean left) {
        Random ran = new Random();
        for (int j = 0; j < map_height; j++) {
            for (int i = 0; i < map_weigh; i++) {
                if (map[j][i].charAt(0) != '#' && map[j][i].charAt(0) != 'W') { // thay doi tat ca ki hieu tru '#' va 'W'
                    char c = map[j][i].charAt(0);
                    if ( c == '/') {                                            // '/' thung se khong sinh ra o vi tri nay
                        map[j][i] = " ";
                    } else if (c == 'B') {                                      // 'B' sinh doi
                        map[j][i] = " ";
                        enemies.add(new Enemy(i, j, this, level));
                    } else if (c == 'G') {                                      // 'G' sinh ma
                        map[j][i] = " ";
                        enemies.add(new Ghost(i, j, this, level));
                    } else if ((c == '1' && up) || (c == '2' && right) || (c == '3' && down) || (c == '4' && left)) {
                        //map[j][i] = " ";
                    } else if (c == '1' || c == '2' || c == '3' || c == '4') {  // neu khong co phong ben canh thi xoa cua
                        map[j][i] = "#";
                    } else {
                        if (ran.nextInt() % 2 != 0 && type == DUNGEON) {
                            map[j][i] = "X";                                    // con lai thay bang thung
                        }
                    }
                }
            }
        }
    }

    /**
     * kiem tra va cham voi quai.
     * @param x
     * @param y
     * @return true neu va cham
     */
    public boolean isMeetEnemy(int x, int y) {
        for (Enemy e : enemies) {
            int x_ = (int) ((e.location_x + 24) / Resources.BLOCK_SIZE);
            int y_ = (int) ((e.location_y + 48) / Resources.BLOCK_SIZE);
            if (x == x_ && y == y_) {
                return true;
            }
        }
        return false;
    }

    /**
     * roi tien, neu truoc do o do da co tien roi thi tang gia tri dong tien do len 1.
     * @param x
     * @param y
     */

    public void makeCoin(int x, int y) {
        if (map[y][x].charAt(0) == 'I') {
            if (map[y][x].charAt(1) == 'G') {
                map[y][x] = "IG" + (char) (map[y][x].charAt(2) + 1);
            }
        } else {
            map[y][x] = "IG" + (char) 1;
        }
    }

    /**
     * nhat tien.
     * @param x
     * @param y
     * @return gia tri cua dong tien;
     */

    public int getCoin(int x, int y) {
        if (map[y][x].charAt(0) == 'I' && map[y][x].charAt(1) == 'G') {
            return map[y][x].charAt(2);
        }
        else return 0;
    }

    /**
     * lay mot phan tu trong map.
     * @param x
     * @param y
     * @return phan tu can lay (String)
     */
    public String get(int x, int y) {
        if (x < 0 || x >= map_weigh || y < 0 || y >= map_height) return "#";
        return map[y][x];
    }

    /**
     * bi chan.
     * @param x
     * @param y
     * @return true neu bi chan.
     */

    public boolean blocked(int x, int y) {
        if (x >= map_weigh || x < 0 || y >= map_height || y < 0) return true;
        return map[y][x].charAt(0) == 'W'
                || map[y][x].charAt(0) == 'X'
                || map[y][x].charAt(0) == 'B'
                || map[y][x].charAt(0) == '#';
    }

    /**
     * nhat item.
     * @param x toa do x
     * @param y toa do y
     * @return 0 neu va cham voi lua, >0 neu nhat duoc item, -1 neu khong nhat duoc gi.
     */

    public int getItem(int x, int y) {
        if (map[y][x].charAt(0) == 'F') return 0;
        if (map[y][x].charAt(0) == 'I') {
            int a = map[y][x].charAt(1) - '0';
            map[y][x] = " ";
            return a;
        } else return -1;
    }

    /**
     * dat bomb xuong.
     * @param x toa do x
     * @param y toa do y
     * @param bomb_number so bomb max
     * @param pow pham vi qua bomb
     * @param id ma nhan vat (moi nhan vat 1 ki hieu khac nhau)
     */

    public void putBomb(int x, int y,int bomb_number , int pow, char id) {
        for (int j = 0; j < map_height; j++) {
            for (int i = 0; i < map_weigh; i++) {
                if (map[j][i].charAt(0) == 'B' && map[j][i].charAt(2) == id) {
                    bomb_number--;
                }
            }
        }

        if (bomb_number > 0 && map[y][x].charAt(0) == ' ') {
            map[y][x] = "B" + ((char) pow) + id;
            map_status[y][x] = 13;
        }
    }

    /**
     * ve room.
     * @param g g
     * @param player nguoi choi
     */


    public void draw(Graphics g, Player player) {
        for (int j = 0; j < map_height; j++) {
            for (int i = 0; i < map_weigh; i++) {
                g.drawImage(Resources.Images.FLOOR.getImage(), i * Resources.BLOCK_SIZE, j * Resources.BLOCK_SIZE, null);
            }
        }
        for (int j = 0; j < map_height; j++) {
            for (int i = 0; i < map_weigh; i++) {
                switch(map[j][i].charAt(0)) {
                    case 'W' -> g.drawImage(Resources.Images.HARD_WALL.getImage(), i * Resources.BLOCK_SIZE, j * Resources.BLOCK_SIZE - 15, null);
                    case 'X' -> g.drawImage(Resources.Images.SOFT_WALL.getImage(), i * Resources.BLOCK_SIZE, j * Resources.BLOCK_SIZE - 15, null);
                    case '#' -> { if (map[j][i].length() == 1)
                        g.drawImage(Resources.Images.WALL2.getImage(), i * Resources.BLOCK_SIZE, j * Resources.BLOCK_SIZE - 15, null);
                    }
                    case '+' -> g.drawImage(Resources.Images.GATE.getImage(), i * Resources.BLOCK_SIZE, j * Resources.BLOCK_SIZE, null);
                }

                switch(map[j][i].charAt(0)) {
                    case 'S' -> g.drawImage(Resources.Animation.BOX.get(3 - map_status[j][i]), i * Resources.BLOCK_SIZE, j * Resources.BLOCK_SIZE - 15, null);
                    case 'B' -> g.drawImage(Resources.Images.BOMB.getImage(), i * Resources.BLOCK_SIZE, j * Resources.BLOCK_SIZE - 15, null);
                    case 'I' -> {
                        switch (map[j][i].charAt(1)) {
                            case 'G' -> g.drawImage(Resources.Images.COIN.getImage(), i * Resources.BLOCK_SIZE, j * Resources.BLOCK_SIZE, null);
                            case '1' -> g.drawImage(Resources.Images.FIREUP.getImage(), i * Resources.BLOCK_SIZE, j * Resources.BLOCK_SIZE, null);
                            case '2' -> g.drawImage(Resources.Images.SPEEDUP.getImage(), i * Resources.BLOCK_SIZE, j * Resources.BLOCK_SIZE, null);
                            case '3' -> g.drawImage(Resources.Images.BOMBUP.getImage(), i * Resources.BLOCK_SIZE, j * Resources.BLOCK_SIZE, null);
                        }
                    }
                    case '#' -> {
                        if (map[j][i].length() > 1)
                            g.drawImage(Resources.Animation.TORCH.get(3 - map_status[j][i]), i * Resources.BLOCK_SIZE, j * Resources.BLOCK_SIZE - 55, null);
                    }
                    case 'F' -> g.drawImage(Resources.Animation.Fire.get(3 - map_status[j][i]), i * Resources.BLOCK_SIZE - 7, j * Resources.BLOCK_SIZE - 15, null);
                }
            }
            if (player.getY() == j) {
                player.draw(g);
            }
            for (Enemy e : enemies) {
                if (e.getY() == j)
                    e.draw(g);
            }
        }
        for (int j = 0; j < map_height; j++) {
            for (int i = 0; i < map_weigh; i++) {
                if (map[j][i].charAt(0) == 'F' || map[j][i].charAt(0) == 'S' )
                    g.drawImage(Resources.Images.LIGHT.getImage(), i * Resources.BLOCK_SIZE - 25, j * Resources.BLOCK_SIZE - 35, null);
                if (map[j][i].charAt(0) == '#' && map[j][i].length() > 1)
                    g.drawImage(Resources.Images.LIGHT.getImage(), i * Resources.BLOCK_SIZE - 25, j * Resources.BLOCK_SIZE - 65, null);
            }
        }
    }

    /**
     * cap nhat room.
     * @param player nguoi choi
     * @return 0 neu khong co gi say ra
     */
    public int update(Player player) {
        for (int i = 0; i < map_weigh; i++) {
            for (int j = 0; j < map_height; j++) {
                if (map_status[j][i] >= 0) {
                    map_status[j][i]-- ;
                }
            }
        }
        for (int i = 0; i < map_weigh; i++) {
            for (int j = 0; j < map_height; j++) {
                if(map_status[j][i] == -1) {
                    if (map[j][i].charAt(0) == 'F') {
                        map[j][i] = " ";
                    }
                    if (map[j][i].charAt(0) == '#' && map[j][i].length() > 1) {
                        map_status[j][i] = 3;
                    }
                    if (map[j][i].charAt(0) == 'B') {
                        explosion(j, i);
                    }
                    if (map[j][i].charAt(0) == 'S') {
                        Random ran = new Random();
                        int r = ran.nextInt() % 10;
                        if (r == 1) map[j][i] = "I1";
                        else if (r == 2) map[j][i] = "I2";
                        else if (r == 3) map[j][i] = "I3";
                        else map[j][i] = " ";
                    }
                }
            }
        }
        for (int i = 0; i < enemies.size(); i++) {
            int u = enemies.get(i).update(player);
            if (u < 0) {
                enemies.remove(i);
                i--;
            }
        }
        return 0;
    }

    /**
     * phat no
     * @param j toa do y
     * @param i toa do x
     */

    private void explosion(int j, int i) {
        int pow = map[j][i].charAt(1);

        map[j][i] = "F";
        map_status[j][i] = 3;

        int a[] = new int[4];
        for (int z = 1; z <= pow; z++) {
            for (int c = 0; c < 4; c++) {
                a[c]++;
            }
            if (immortal(j, i + a[0])) a[0]--;
            if (immortal(j, i - a[1])) a[1]--;
            if (immortal(j + a[2], i)) a[2]--;
            if (immortal(j - a[3], i)) a[3]--;

            map[j][i + a[0]] = "F";
            map[j][i - a[1]] = "F";
            map[j + a[2]][i] = "F";
            map[j - a[3]][i] = "F";

            map_status[j][i + a[0]] = 3;
            map_status[j][i - a[1]] = 3;
            map_status[j + a[2]][i] = 3;
            map_status[j - a[3]][i] = 3;
        }
    }

    /**
     * bat kha xam pham
     * @param j toa do y
     * @param i toa do x
     * @return true neu vat the khong the bi pha huy.
     */

    private boolean immortal(int j, int i) {
        if(j <= 0 || i <= 0 || j >= map_height - 1 || i >= map_weigh - 1) return true; // moi phan tu ngoai map deu bat kha xam pham
        if(map[j][i].charAt(0) == 'X') { // gap thung thi dot chay thung do X là thung, S là thung dang chay
            map[j][i] = "S";
            map_status[j][i] = 3;
        }

        if(map[j][i].charAt(0) == 'B') { // neu gap bomb thi kich no qua bomb do
            explosion(j, i);
        }

        if(map[j][i].charAt(0) == 'W' || map[j][i].charAt(0) == '#' || map[j][i].charAt(0) == 'S' || map[j][i].charAt(0) == '+') // neu gap tuong thi tra ve true
            return true;
        else return false;
    }
}