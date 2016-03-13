package spells.projectile;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class PoisonArrow extends Projectile {
    private float damage, amount;
    private String command = "damage";

    public PoisonArrow(Image image, String creator, Vector2f position, float angle, float speed, float range,
            float damage, float areaOfEffect) throws SlickException {
        super(image, creator, position, angle, speed, range, areaOfEffect);
        this.damage = damage;
        this.amount = damage;
    }

    @Override
    public String getMessage() {
        return command + " " + amount;
    }

    public void trigger() {
        if (!finished && command != "drain") {
            command = "drain";
            amount = damage * 1.5f;
        } else {
            finished = true;
        }
    }
}
