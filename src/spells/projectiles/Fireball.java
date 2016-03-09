package spells.projectiles;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class Fireball extends Projectile {
    private float damage;
    private boolean finished;

    public Fireball(Image image, String creator, Vector2f position, Vector2f facing, float speed, float range, float damage,
            float areaOfEffect) throws SlickException {
        super(image, creator, position, facing, speed, range, areaOfEffect);
        this.damage = damage;
    }

    @Override
    public String getMessage() {
        return "damage " + damage;
    }
    
    public void trigger() {
        finished = true;
    }

    @Override
    public boolean finished() {
        return finished;
    }

}
