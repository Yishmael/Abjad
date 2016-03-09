package spells.selfcast;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

public class Heal extends Selfcast {
    public float healing;
    public boolean finished = false;

    public Heal(Image image, String creator, Vector2f position, Vector2f facing, float healing, float areaOfEffect) {
        super(image, creator, position, facing, areaOfEffect);
        this.healing = healing;
    }

    @Override
    public String getMessage() {
        trigger();
        return "heal " + healing;
    }

    @Override
    public boolean finished() {
        return finished;
    }

    @Override
    public void trigger() {
        finished = true; 
    }

}
