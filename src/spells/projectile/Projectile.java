package spells.projectile;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import spells.Spell;

public abstract class Projectile extends Spell {
    protected float speed, range;

    protected Vector2f startingPos;

    public Projectile(Image image, String creator, Vector2f position, float angle, float speed, float range,
            float areaOfEffect) throws SlickException {
        super(image, creator, position, angle, areaOfEffect);
        this.startingPos = new Vector2f(position);
        this.speed = speed;
        this.range = range;
        super.setSpeed(speed);
    }

    public float getSpeed() {
        return speed;
    }

    public void update() {
        float dx = startingPos.getX() - super.getPosition().getX();
        float dy = startingPos.getY() - super.getPosition().getY();

        if (Math.sqrt(dx * dx + dy * dy) > range) {
            finished = true;
        }

        super.update();
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
