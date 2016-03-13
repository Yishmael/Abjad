package spells.summon;

import org.lwjgl.Sys;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import spells.Spell;

public abstract class Summon extends Spell {

    protected float duration;
    protected long createTime;

    public Summon(Image image, String creator, Vector2f position, float angle, float areaOfEffect, float duration) {
        super(image, creator, position, angle, areaOfEffect);
        this.duration = duration;
        createTime = (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    public void update() {
        if ((Sys.getTime() * 1000) / Sys.getTimerResolution() - createTime >= duration * 1000f) {
            finished = true;
        }
        super.update();
    }

    public boolean finished() {
        return finished;
    }
}
