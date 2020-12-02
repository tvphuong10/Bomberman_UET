public class Slime extends Enemy{
    Slime(int x, int y, Room room, int level) {
        super(x, y, room, level);
        this.animation = Resources.Animation.SLIME.getAnimation();
        this.dead_animation = Resources.Animation.BATDEAD.getAnimation();
        this.speed = 5 * level;
    }

    @Override
    protected void hurt() {
        Resources.Sound.SLIME.reStart();
    }

}
