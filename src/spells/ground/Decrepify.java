package spells.ground;

import org.lwjgl.Sys;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

public class Decrepify extends Ground {
    private long createTime;
    private int counter;

    public Decrepify(Image image, String creator, Vector2f position, float angle, float areaOfEffect, float duration) {
        super(image, creator, position, angle, areaOfEffect, duration);
        createTime = (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    @Override
    public String getMessage() {
        if (counter < 42) {
            return "decrepify " + 100 + " " + 5;
        }
        return null;
    }

    @Override
    public void trigger() {
        counter++;
    }

    @Override
    public void update() {
        if ((Sys.getTime() * 1000) / Sys.getTimerResolution() - createTime >= duration * 1000f) {
            finished = true;
        }
        super.update();
    }
}
