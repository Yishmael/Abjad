package spells.ground;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import spells.Spell;

public abstract class Ground extends Spell {

    public Ground(Image image, String creator, Vector2f position, Vector2f facing, float areaOfEffect) {
        super(image,creator, position, facing, areaOfEffect);
        this.areaOfEffect = areaOfEffect;
    }

    public void update() {
        super.update();
    }
}
