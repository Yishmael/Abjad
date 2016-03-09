package spells.projectiles;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import spells.Spell;

public abstract class Projectile extends Spell {
    protected float speed, range;

    public Projectile(Image image, String creator, Vector2f position, Vector2f facing, float speed, float range, float areaOfEffect) throws SlickException {
        super(image, creator, position, facing, areaOfEffect);
        this.speed = speed;
        this.range = range;
    }

    public float getSpeed() {
        return speed;
    }

    public float getRange() {
        return range;
    }
    
    public void update() {
        super.setSpeed(speed);
        super.update();
    }
    
}
