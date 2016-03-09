package spells.ground;

import org.lwjgl.Sys;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

public class BurningGround extends Ground {
    private long lastTime = 0, now = 0;
    private float damage, remaining;

    public BurningGround(Image image, String creator, Vector2f position, Vector2f facing, float areaOfEffect, float damage,
            float duration) {
        super(image, creator, position, facing, areaOfEffect);
        this.damage = damage;
        this.remaining = duration;
        lastTime = (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    @Override
    public String getMessage() {
        now = (Sys.getTime() * 1000) / Sys.getTimerResolution();
        if (now - lastTime >= 500) {
            remaining -= (now - lastTime) / 1000f;
            lastTime = now;
            return "damage " + (damage / 2);
        }
        return null;
    }

    @Override
    public boolean finished() {
        return remaining <= 0;
    }

    @Override
    public void trigger() {
        System.out.println("Burning Ground triggered");
    }

}
