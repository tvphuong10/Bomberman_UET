import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * lưu tài nguyên game (những thứ đọc từ bên ngoài).
 */

public class Resources {

    static final int BLOCK_SIZE = 50;
    static final int SCREEN_H = 685;
    static final int SCREEN_W = 900;

    public enum Images {
        //BACKGROUND,
        MINIMAP,
        MINIMAP2,
        BOMB,
        BOMBUP,
        FIREUP,
        FLOOR,
        FLOOR2,
        HARD_WALL,
        ICON,
        SOFT_WALL,
        SPEEDUP,
        TRANSITIONS,
        WALL2;

        private BufferedImage image = null;

        public BufferedImage getImage() {
            return this.image;
        }
    }

    /**
     * animation.
     */

    public enum Animation {
        BOX,
        Fire,
        BAT,
        BATDEAD,
        LEESIN,
        LEESINDEAD,
        RUN,
        TEEMO,
        TEEMODEAD;

        private BufferedImage[] animation = new BufferedImage[4];

        public BufferedImage get(int i) {
            return this.animation[i];
        }

        public BufferedImage[] getAnimation() {
            return this.animation;
        }
    }

    /**
     * hàm đọc file.
     */

    public static void readFiles() {
        try {
            String res = "/Img/";
            System.out.println(Resources.class.getResource(""));
            Images.FLOOR.image = ImageIO.read(Resources.class.getResource(res + "Floor.png"));
            Images.FLOOR2.image = ImageIO.read(Resources.class.getResource(res + "Floor2.png"));
            Images.TRANSITIONS.image = ImageIO.read(Resources.class.getResource(res + "Transitions.png"));
            Images.ICON.image = ImageIO.read(Resources.class.getResource(res + "Box.png"));
            Images.FIREUP.image = ImageIO.read(Resources.class.getResource(res + "FireUp.png"));
            Images.BOMBUP.image = ImageIO.read(Resources.class.getResource(res + "BombUp.png"));
            Images.SPEEDUP.image = ImageIO.read(Resources.class.getResource(res + "SpeedUp.png"));
            Images.SOFT_WALL.image = ImageIO.read(Resources.class.getResource(res + "Box.png"));
            Images.HARD_WALL.image = ImageIO.read(Resources.class.getResource(res + "Wall1.png"));
            Images.WALL2.image = ImageIO.read(Resources.class.getResource(res + "Wall2.png"));
            Images.BOMB.image = ImageIO.read(Resources.class.getResource(res + "Musroom.png"));
            Images.MINIMAP2.image = ImageIO.read(Resources.class.getResource(res + "Map2.png"));
            Images.MINIMAP.image = ImageIO.read(Resources.class.getResource(res + "Map.png"));

            res = "/Animations/";
            for (int i = 1; i <= 4; i++) {
                Animation.BOX.animation[i - 1] = ImageIO.read(Resources.class.getResource( res +"box" + i + ".png"));
                Animation.Fire.animation[i - 1] = ImageIO.read(Resources.class.getResource(res + "Fire" + i + ".png"));
                Animation.TEEMO.animation[i - 1] = ImageIO.read(Resources.class.getResource(res + "teemoAnimation" + i + ".png"));
                Animation.TEEMODEAD.animation[i - 1] = ImageIO.read(Resources.class.getResource(res + "TeemoDead" + i + ".png"));
                Animation.LEESIN.animation[i - 1] = ImageIO.read(Resources.class.getResource(res + "LeesinAnimation" + i + ".png"));
                Animation.LEESINDEAD.animation[i - 1] = ImageIO.read(Resources.class.getResource(res + "LeesinDead" + i + ".png"));
                Animation.BAT.animation[i - 1] = ImageIO.read(Resources.class.getResource(res + "Bat" + i + ".png"));
                Animation.BATDEAD.animation[i - 1] = ImageIO.read(Resources.class.getResource(res + "BatDead" + i + ".png"));
            }

            for (int i = 1; i <= 3; i++) {
                Animation.RUN.animation[i - 1] = ImageIO.read(Resources.class.getResource(res + "Run" + i + ".png"));
            }

        } catch (IOException e) {
            System.err.println(e + ": Cannot read image file");
            e.printStackTrace();
        }
    }
}
