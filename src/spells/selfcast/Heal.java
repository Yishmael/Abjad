package spells.selfcast;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

public class Heal extends Selfcast {
    public float healing;

    public Heal(Image image, String creator, Vector2f position, float angle, float healing, float areaOfEffect) {
        super(image, creator, position, angle, areaOfEffect);
        this.healing = healing;
    }

    @Override
    public String getMessage() {
        finished = true;
        return "heal " + healing;
    }

    @Override
    public void trigger() {
        finished = true;
    }

}
