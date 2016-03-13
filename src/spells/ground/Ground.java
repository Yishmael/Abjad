package spells.ground;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import spells.Spell;

public abstract class Ground extends Spell {
    protected float duration;

    public Ground(Image image, String creator, Vector2f position, float angle, float areaOfEffect, float duration) {
        super(image, creator, position, angle, areaOfEffect);
        this.duration = duration;
    }

    public float getDuration() {
        return duration;
    }
}
