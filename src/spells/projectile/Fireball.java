package spells.projectile;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class Fireball extends Projectile {
    private float damage;

    public Fireball(Image image, String creator, Vector2f position, float angle, float speed, float range, float damage,
            float areaOfEffect) throws SlickException {
        super(image, creator, position, angle, speed, range, areaOfEffect);
        this.damage = damage;
        
    }

    @Override
    public String getMessage() {
        return "damage " + damage;
    }
    
    public void trigger() {
        finished = true;
    }

}
