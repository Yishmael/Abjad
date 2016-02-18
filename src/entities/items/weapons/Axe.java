package entities.items.weapons;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Axe extends Weapon {
	private float critChance;

	public Axe(Image image, int xPos, int yPos, int durability, int damage, float critChance) throws SlickException {
		super(image, xPos, yPos, durability, damage);
		this.critChance = critChance;
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

}
