package spells.selfcast;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import spells.Spell;

public abstract class Selfcast extends Spell {

    public Selfcast(Image image, String creator, Vector2f position, Vector2f facing, float areaOfEffect) {
        super(image, creator, position, facing, areaOfEffect);
    }

    public void update() {
        super.update();
    }

}
