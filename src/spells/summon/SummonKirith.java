package spells.summon;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

public class SummonKirith extends Summon {

    public SummonKirith(Image image, String creator, Vector2f position, float angle, float areaOfEffect,
            float duration) {
        super(image, creator, position, angle, areaOfEffect, duration);
    }

    @Override
    public void trigger() {
    }

    @Override
    public String getMessage() {
        return null;
    }

}
