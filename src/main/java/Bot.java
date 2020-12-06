public class Bot extends Player {

    private int[][] enemy;

    public Bot() {
        super();
        enemy = new int[13][13];
    }

    @Override
    protected boolean condition(int x, int y) {
        int x_ = (int) ((location_x + 24) / Resources.BLOCK_SIZE);
        int y_ = (int) ((location_y + 48) / Resources.BLOCK_SIZE);
        if (enemy[x_][y_] == 1 && immortal_timer < 3) {
            return enemy[x][y] == 0;
        } else if (room.getWarning(x_, y_) >= 0) return room.getWarning(x, y) == -1;
        else {
            if (room.get(x, y).charAt(0) == room.getMainGate() + '0') return true;
            if (room.get(x - 1, y).charAt(0) == 'X'
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
            if (room.getWarning(x, y) != -1)
            if (room.getWarning(x, y) <= 3) return true;
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
            enemy[x_][y_ + 1] = 1;
            enemy[x_][y_ - 1] = 1;
            enemy[x_ + 1][y_] = 1;
            enemy[x_ - 1][y_] = 1;
            enemy[x_][y_] = 1;
        }
    }

    private boolean check(int x, int y) {
        return  room.getWarning(x, y) == -1
                && room.getWarning(x, y - 1) == -1
                && room.getWarning(x, y + 1) == -1
                && room.getWarning(x + 1, y) == -1
                && room.getWarning(x - 1, y) == -1
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
        if (life == 0 && frame >= 11) {
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
                    x_room = 11;
                    return 4;
                } else if (y2 >= room.getMapHeight()) {
                    location_y = -10;
                    y_room = 1;
                    return 3;
                } else if (x2 >= room.getMapWeigh()) {
                    location_x = 0;
                    x_room = 1;
                    return 2;
                } else if (y1 < 0) {
                    location_y = room.getMapHeight() * Resources.BLOCK_SIZE - 100;
                    y_room = 11;
                    return 1;
                } else if (room.get(x, y).charAt(0) == '5') return 5;
            } else {
                frozen--;
            }
        }
        return 0;
    }
}
