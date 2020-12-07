public class Bot extends Player {

    private int[][] enemy;
    private int[][] bomb;

    public Bot() {
        super();
        enemy = new int[13][13];
        bomb = new int[13][13];
    }

    @Override
    protected boolean condition(int x, int y) {
        findBomb();
        int x_ = (int) ((location_x + 24) / Resources.BLOCK_SIZE);
        int y_ = (int) ((location_y + 48) / Resources.BLOCK_SIZE);
        if (enemy[x_][y_] == 1 && immortal_timer < 3) {
            return enemy[x][y] == 0;
        } else if (bomb[x_][y_] >= 0)
            return bomb[x][y] == -1;
        else {
            if (room.get(x, y).charAt(0) == room.getMainGate() + '0') return true;
            if ( room.get(x, y).charAt(0) == 'I'
                    || room.get(x - 1, y).charAt(0) == 'X'
                    || room.get(x + 1, y).charAt(0) == 'X'
                    || room.get(x, y + 1).charAt(0) == 'X'
                    || room.get(x, y - 1).charAt(0) == 'X') return true;
        }
        return false;
    }

    @Override
    protected boolean blocked(int x, int y, boolean[][] arr) {
        char c = room.get(x, y).charAt(0);
        if (c == 'F' || c == 'B' || c == 'S') return true;
        if (x >= 0 && y >= 0 && x < 13 && y < 13) {
            if (bomb[x][y] != -1)
            if (bomb[x][y] <= 3) return true;
            if(enemy[x][y] != 0 && immortal_timer < 3) {
                return true;
            }
        }
        return super.blocked(x, y, arr);
    }

    private void findEnemy() {
        enemy = new int[13][13];
        for (Enemy e : room.enemies) {
            int x_ = (int) ((e.location_x + 24) / Resources.BLOCK_SIZE);
            int y_ = (int) ((e.location_y + 48) / Resources.BLOCK_SIZE);
            if (y_ + 1 < 13) enemy[x_][y_ + 1] = 1;
            if (y_ - 1 >= 0) enemy[x_][y_ - 1] = 1;
            if (x_ + 1 < 13) enemy[x_ + 1][y_] = 1;
            if (x_ - 1 >= 0) enemy[x_ - 1][y_] = 1;
            enemy[x_][y_] = 1;
        }
    }

    private boolean check(int x, int y) {
        if (x < 1 || y < 1 || x >= 12 || y >= 12) return true;
        return  bomb[x][y] == -1
                && bomb[x][y - 1] == -1
                && bomb[x][y + 1] == -1
                && bomb[x + 1][y] == -1
                && bomb[x - 1][y] == -1
                && room.get(x, y).charAt(0) != 'F'
                && (room.get(x, y).charAt(0) == 'I'
                || room.get(x, y - 1).charAt(0) == 'X'
                || room.get(x, y + 1).charAt(0) == 'X'
                || room.get(x - 1, y).charAt(0) == 'X'
                || room.get(x + 1, y).charAt(0) == 'X') ;
    }

    @Override
    protected int update() {
        int x = (int) ((location_x + 24) / Resources.BLOCK_SIZE);
        int y = (int) ((location_y + 48) / Resources.BLOCK_SIZE);

        if (immortal_timer > 0) immortal_timer--;
        if (life <= 0 && frame >= 11) {
            return -1;
        } else {
            if (immortal_timer == 0) {
                pickItem();
                int h = room.isMeetEnemy(x, y);
                if (h == 1) dead();
                if(h == 2) frozen = 70;
            }
            if (frozen == 0) {
                if (!up && !right && !down && !left) {
                    findBomb();
                    if (check(x, y)) putBomb();
                    findEnemy();
                    findTheWay(19);
                }
                move();
                int x1 = (int) ((location_x + 10)  / Resources.BLOCK_SIZE);
                int x2 = (int) ((location_x + 40) / Resources.BLOCK_SIZE);
                int y1 = (int) ((location_y + 40) / Resources.BLOCK_SIZE);
                int y2 = (int) ((location_y + 60) / Resources.BLOCK_SIZE);
                if (x1 < 0) {
                    location_x = room.getMapWeigh() * Resources.BLOCK_SIZE - 100;
                    x_room = 12;
                    return 4;
                } else if (y2 >= room.getMapHeight()) {
                    location_y = -10;
                    y_room = 0;
                    return 3;
                } else if (x2 >= room.getMapWeigh()) {
                    location_x = 0;
                    x_room = 0;
                    return 2;
                } else if (y1 < 0) {
                    location_y = room.getMapHeight() * Resources.BLOCK_SIZE - 100;
                    y_room = 12;
                    return 1;
                } else if (room.get(x, y).charAt(0) == '5') return 5;
            } else {
                frozen--;
            }
        }
        return 0;
    }

    private void findBomb() {
        bomb = new int[13][13];
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 13; j++) {
                bomb[i][j] = -1;
            }
        }

        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 13; j++) {
                int timer = room.getBombStatus(i, j);
                if (room.get(i, j).charAt(0) == 'B')
                    findExplosion(i, j, room.get(i, j).charAt(1), timer);
            }
        }
    }

    private void findExplosion(int x, int y, int range, int t) {
        int timer = t;
        if (bomb[x][y] != -1) timer = bomb[x][y];
        int a[] = new int[4];
        for (int z = 1; z <= range; z++) {
            for (int c = 0; c < 4; c++) {
                a[c]++;
            }
            if ((x + a[0]) >= 13 || (room.get(x + a[0], y).charAt(0) != ' ' && room.get(x + a[0], y).charAt(0) != 'I'))
                a[0]--;
            if ((x - a[1]) < 0   || (room.get(x - a[1], y).charAt(0) != ' ' && room.get(x - a[1], y).charAt(0) != 'I'))
                a[1]--;
            if ((y + a[2]) >= 13 || (room.get(x, y + a[2]).charAt(0) != ' ' && room.get(x, y + a[2]).charAt(0) != 'I'))
                a[2]--;
            if ((y - a[3]) < 0   || (room.get(x, y - a[3]).charAt(0) != ' ' && room.get(x, y - a[3]).charAt(0) != 'I'))
                a[3]--;
            bomb[x][y] = timer;
            if (bomb[x + a[0]][y] == -1 || bomb[x + a[0]][y] >= timer) bomb[x + a[0]][y] = timer;
            if (bomb[x - a[1]][y] == -1 || bomb[x - a[1]][y] >= timer) bomb[x - a[1]][y] = timer;
            if (bomb[x][y + a[2]] == -1 || bomb[x][y + a[2]] >= timer) bomb[x][y + a[2]] = timer;
            if (bomb[x][y - a[3]] == -1 || bomb[x][y - a[3]] >= timer) bomb[x][y - a[3]] = timer;
        }
    }
}
