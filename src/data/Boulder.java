package data;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Boulder extends Weapon {
    private float speed;

    public Boulder(Image image, String name, float xPos, float yPos, int durability, int damage, float speed)
            throws SlickException {
        super(image, name, xPos, yPos, durability, damage);
        this.speed = speed;
    }

    @Override
    public void attack() {
        // TODO Auto-generated method stub

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
