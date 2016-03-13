package spells.selfcast;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import spells.Spell;

public abstract class Selfcast extends Spell {

    public Selfcast(Image image, String creator, Vector2f position, float angle, float areaOfEffect) {
        super(image, creator, position, angle, areaOfEffect);
    }
}
