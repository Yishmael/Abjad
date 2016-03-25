package spells.selfcast;

import org.lwjgl.Sys;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

public class HolyShield extends Selfcast {
    private float duration;
    private long createTime;

    public HolyShield(Image image, String creator, Vector2f position, float angle, float areaOfEffect, float duration) {
        super(image, creator, position, angle, areaOfEffect);
        this.duration = duration;
        createTime = (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    @Override
    public String getMessage() {
        finished = true;
        return "lightningdmg 30";
    }

    @Override
    public void trigger() {

    }

    public void update() {
        if ((Sys.getTime() * 1000) / Sys.getTimerResolution() - createTime >= duration * 1000f) {
            finished = true;
        }
        super.update();
    }

}
