package spells.projectile;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import spells.Spell;

public abstract class Projectile extends Spell {
    protected float speed, range;

    public Projectile(Image image, String creator, Vector2f position, float angle, float speed, float range,
            float areaOfEffect) throws SlickException {
        super(image, creator, position, angle, areaOfEffect);
        this.speed = speed;
        this.range = range;
        super.setSpeed(speed);
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getRange() {
        return range;
    }

    public void setRange(float range) {
        this.range = range;
    }
}
