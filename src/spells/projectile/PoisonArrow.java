package spells.projectile;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class PoisonArrow extends Projectile {
    private float damage;
    private String command = "poisondmg";

    public PoisonArrow(Image image, String creator, Vector2f position, float angle, float speed, float range,
            float damage, float areaOfEffect) throws SlickException {
        super(image, creator, position, angle, speed, range, areaOfEffect);
        this.damage = damage;
    }

    @Override
    public String getMessage() {
        return command + " " + damage;
    }

    public void trigger() {
        if (!finished && command != "dot 5") {
            command = "dot 5";
            damage = 3;
        } else {
            finished = true;
        }
    }
}
