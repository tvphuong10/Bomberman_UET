import java.awt.*;
import java.util.Random;

public class Level extends Object {
    public final int LEVEL_SIZE = 5;
    private int i;
    private Room[][] rooms;
    private Room room;
    private Player player;

    public Player getPlayer() {return player;}

    public Level() {
        i = 0;
        rooms = new Room[LEVEL_SIZE][LEVEL_SIZE];
        rooms[4][2] = new Room(Room.START);

        buildLevel();
        player = new Player();
        player.Init(rooms[4][2],Resources.Animation.TEEMO.getAnimation(),
                Resources.Animation.RUN.getAnimation(),
                Resources.Animation.TEEMODEAD.getAnimation());
        player.setRoom(4,2,rooms[4][2]);
    }

    public void buildLevel() {
        Random ran = new Random();
        boolean up = false;
        boolean right = false;
        boolean down = true;
        boolean left = true;

        int x = ran.nextInt(5);
        int y = ran.nextInt(2);
        rooms[y][x] = new Room(Room.END);
        while (y != 4 || x != 2) {
            int r = ran.nextInt(2);
            if (r == 0 && y < 4) y++;
            if (r == 1 && x < 2) x++;
            if (r == 1 && x > 2) x--;
            rooms[y][x] = new Room(Room.DUNGEON);
        }

        rooms[4][2] = new Room(Room.START);
        for (int i = 0; i < LEVEL_SIZE; i++) {
            for (int j = 0; j < LEVEL_SIZE; j++) {
                if (rooms[i][j] != null) {
                    up = false;
                    right = false;
                    down = false;
                    left = false;
                    if (i - 1 > 0 && rooms[i - 1][j] != null) up = true;
                    if (i + 1 < LEVEL_SIZE && rooms[i + 1][j] != null) down = true;
                    if (j - 1 > 0 && rooms[i][j - 1] != null) left = true;
                    if (j + 1 < LEVEL_SIZE && rooms[i][j + 1] != null) right = true;
                    rooms[i][j].buildRoom(up,right,down,left);
                }
            }
        }
    }

    @Override
    protected int update() {
        i++;
        if (i % 4 == 0) {
            for (int i = 0; i < LEVEL_SIZE; i++) {
                for (int j = 0; j < LEVEL_SIZE; j++) {
                    if (rooms[i][j] != null) {
                        rooms[i][j].update();
                    }
                }
            }
            i = 0;
        }
        int u = player.update();
        if (u > 0) {
            int y = player.getLevel_y();
            int x = player.getLevel_x();
            if (u == 1) x--;
            else if (u == 2) y++;
            else if (u == 3) x++;
            else if (u == 4) y--;
            player.setRoom(x, y, rooms[x][y]);
            player.enterRoom((u + 1) % 4 + 1);
        }
        return 0;
    }

    protected void draw(Graphics g) {
        player.room.draw(g, player);

        for (int i = 0; i < LEVEL_SIZE; i++) {
            for (int j = 0; j < LEVEL_SIZE; j++) {
                if (rooms[j][i] != null)
                if (j == player.getLevel_x() && i == player.level_y) {
                    g.drawImage(Resources.Images.MINIMAP2.getImage(), 700 + i * 30, 30 + j * 30, null);
                } else {
                    g.drawImage(Resources.Images.MINIMAP.getImage(), 700 + i * 30, 30 + j * 30, null);
                }
            }
        }
        drawHub(g);
    }

    private void drawHub(Graphics g) {
        g.setColor(Color.GRAY);
        g.setFont(new Font("Arial",Font.BOLD, 24));
        g.drawString("Life: " + player.life , 680, 220);
        g.drawString("Bomb: " + player.bombNumber , 680, 260);
        g.drawString("Pow: " + player.power , 680, 300);
        g.drawString("Speed: " + player.speed , 680, 340);
    }
}
