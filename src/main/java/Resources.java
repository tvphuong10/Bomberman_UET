import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * lưu tài nguyên game (những thứ đọc từ bên ngoài).
 */

public class Resources {

    static final int BLOCK_SIZE = 50;
    static final int SCREEN_H = 695;
    static final int SCREEN_W = 662;

    public enum Images {
        SHOPKEEPER,
        SHOPKEEPER2,
        BACKGROUND,
        BOMBUP,
        BUTTON,
        BUTTONS,
        COIN,
        FIREUP,
        FLOOR,
        FLOOR2,
        GATE,
        HARD_WALL,
        ICON,
        LIFE,
        LIGHT,
        MINIMAP,
        MINIMAP2,
        PAUSE,
        SHOP,
        SOFT_WALL,
        SPEEDUP,
        STARTFLOOR,
        TRANSITION,
        TRANSITIONS,
        ICE,
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
        BAT,
        BATDEAD,
        BOX,
        CRYSTAL,
        Fire,
        GHOST,
        GHOSTDEAD,
        LEESIN,
        LEESINDEAD,
        MENU,
        RUN,
        TEEMO,
        TEEMODEAD,
        ICE,
        SLIME,
        TORCH;

        private BufferedImage[] animation = new BufferedImage[4];

        public BufferedImage get(int i) {
            return this.animation[i];
        }

        public BufferedImage[] getAnimation() {
            return this.animation;
        }
    }

    public enum Sound {
        MENU,
        EXPLOSION,
        COIN,
        BAT,
        GHOST,
        HURT,
        CRYSTAL,
        SLIME,
        PLAYING;

        private Clip clip;
        public void start() {
            clip.start();
        }
        public void reStart() {
            clip.stop();
            clip.setFramePosition(0);
            clip.start();
        }
        public void setLoop() {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        public void stop() {
            clip.stop();
        }
    }

    /**
     * hàm đọc file.
     */

    public static void readFiles() {
        try {
            String res = "/Img/";
            System.out.println(Resources.class.getResource(""));
            Images.STARTFLOOR.image = ImageIO.read(Resources.class.getResource(res + "Start.png"));
            Images.SHOPKEEPER.image = ImageIO.read(Resources.class.getResource(res + "ShopKeeper.png"));
            Images.SHOPKEEPER2.image = ImageIO.read(Resources.class.getResource(res + "ShopKeeper2.png"));
            Images.TRANSITION.image = ImageIO.read(Resources.class.getResource(res + "Transitions.png"));
            Images.GATE.image = ImageIO.read(Resources.class.getResource(res + "Gate.png"));
            Images.LIFE.image = ImageIO.read(Resources.class.getResource(res + "Life.png"));
            Images.LIGHT.image = ImageIO.read(Resources.class.getResource(res + "LIGHT.png"));
            Images.COIN.image = ImageIO.read(Resources.class.getResource(res + "Coin.png"));
            Images.BACKGROUND.image = ImageIO.read(Resources.class.getResource(res + "MapBg.png"));
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
            Images.MINIMAP2.image = ImageIO.read(Resources.class.getResource(res + "Map2.png"));
            Images.MINIMAP.image = ImageIO.read(Resources.class.getResource(res + "Map.png"));
            Images.ICE.image = ImageIO.read(Resources.class.getResource(res + "Ice.png"));

            res = "/Hub/";
            Images.PAUSE.image = ImageIO.read(Resources.class.getResource(res + "Pause.png"));
            Images.SHOP.image = ImageIO.read(Resources.class.getResource(res + "Shop.png"));
            Images.BUTTON.image = ImageIO.read(Resources.class.getResource(res + "ShopButton.png"));
            Images.BUTTONS.image = ImageIO.read(Resources.class.getResource(res + "ShopButtonS.png"));

            res = "/Animations/";
            for (int i = 1; i <= 4; i++) {
                Animation.MENU.animation[i - 1] = ImageIO.read(Resources.class.getResource( res +"Menu" + i + ".png"));
                Animation.CRYSTAL.animation[i - 1] = ImageIO.read(Resources.class.getResource( res +"Crystal" + i + ".png"));
                Animation.BOX.animation[i - 1] = ImageIO.read(Resources.class.getResource( res +"box" + i + ".png"));
                Animation.TORCH.animation[i - 1] = ImageIO.read(Resources.class.getResource( res +"Torch" + i + ".png"));
                Animation.Fire.animation[i - 1] = ImageIO.read(Resources.class.getResource(res + "Fire" + i + ".png"));
                Animation.TEEMO.animation[i - 1] = ImageIO.read(Resources.class.getResource(res + "Alice" + i + ".png"));
                Animation.TEEMODEAD.animation[i - 1] = ImageIO.read(Resources.class.getResource(res + "TeemoDead" + i + ".png"));
                Animation.LEESIN.animation[i - 1] = ImageIO.read(Resources.class.getResource(res + "LeesinAnimation" + i + ".png"));
                Animation.LEESINDEAD.animation[i - 1] = ImageIO.read(Resources.class.getResource(res + "LeesinDead" + i + ".png"));
                Animation.BAT.animation[i - 1] = ImageIO.read(Resources.class.getResource(res + "Bat" + i + ".png"));
                Animation.BATDEAD.animation[i - 1] = ImageIO.read(Resources.class.getResource(res + "BatDead" + i + ".png"));
                Animation.GHOST.animation[i - 1] = ImageIO.read(Resources.class.getResource(res + "Ghost" + i + ".png"));
                Animation.GHOSTDEAD.animation[i - 1] = ImageIO.read(Resources.class.getResource(res + "GhostDead" + i + ".png"));
                Animation.ICE.animation[i-1] = ImageIO.read(Resources.class.getResource(res + "Iceboss" + i + ".png"));
                Animation.SLIME.animation[i-1] = ImageIO.read(Resources.class.getResource(res + "Slime" + i + ".png"));
            }

            for (int i = 1; i <= 3; i++) {
                Animation.RUN.animation[i - 1] = ImageIO.read(Resources.class.getResource(res + "Run" + i + ".png"));
            }

            Sound.EXPLOSION.clip = AudioSystem.getClip();
            Sound.MENU.clip = AudioSystem.getClip();
            Sound.COIN.clip = AudioSystem.getClip();
            Sound.BAT.clip = AudioSystem.getClip();
            Sound.GHOST.clip = AudioSystem.getClip();
            Sound.HURT.clip = AudioSystem.getClip();
            Sound.CRYSTAL.clip = AudioSystem.getClip();
            Sound.SLIME.clip = AudioSystem.getClip();
            res = "/Sound/";
            Sound.EXPLOSION.clip.open(AudioSystem.getAudioInputStream(Resources.class.getResource( res +"Explosion.wav")));
            Sound.MENU.clip.open(AudioSystem.getAudioInputStream(Resources.class.getResource( res +"MenuTheme.wav")));
            Sound.COIN.clip.open(AudioSystem.getAudioInputStream(Resources.class.getResource( res +"Coin.wav")));
            Sound.BAT.clip.open(AudioSystem.getAudioInputStream(Resources.class.getResource( res +"Bat.wav")));
            Sound.GHOST.clip.open(AudioSystem.getAudioInputStream(Resources.class.getResource( res +"Ghost.wav")));
            Sound.HURT.clip.open(AudioSystem.getAudioInputStream(Resources.class.getResource( res +"Hurt.wav")));
            Sound.CRYSTAL.clip.open(AudioSystem.getAudioInputStream(Resources.class.getResource( res +"Crystal.wav")));
            Sound.SLIME.clip.open(AudioSystem.getAudioInputStream(Resources.class.getResource( res +"Slime.wav")));

        } catch (IOException e) {
            System.err.println(e + ": Cannot read image file");
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
