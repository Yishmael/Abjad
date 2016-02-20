package data;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Shield extends Armor {

    private float reflection;

    public Shield(Image image, String name, float xPos, float yPos, int durability, int defense, float reflection)
            throws SlickException {
        super(image, name, xPos, yPos, durability, defense);
        this.reflection = reflection;
    }

    public void reflect() {

    }

    @Override
    public void render(Graphics g) {
        // TODO Auto-generated method stub

    }

    @Override
    public void update(int dt) {
        // TODO Auto-generated method stub

    }

}
