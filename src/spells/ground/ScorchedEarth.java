package spells.ground;

import org.lwjgl.Sys;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

public class ScorchedEarth extends Ground {
    private long lastTime, now, createTime;
    private float damage;

    public ScorchedEarth(Image image, String creator, Vector2f position, float angle, float areaOfEffect,
            float damage, float duration) {
        super(image, creator, position, angle, areaOfEffect, duration);
        this.damage = damage;
        createTime = (Sys.getTime() * 1000) / Sys.getTimerResolution();
        lastTime = (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    @Override
    public String getMessage() {
        now = (Sys.getTime() * 1000) / Sys.getTimerResolution();
        if (now - lastTime >= 500) {
            lastTime = now;
            return "damage " + (damage / 2);
        }
        return null;
    }

    @Override
    public void update() {
        if ((Sys.getTime() * 1000) / Sys.getTimerResolution() - createTime >= duration * 1000f) {
            finished = true;
        }
        super.update();
    }

    @Override
    public void trigger() {
    }

}
