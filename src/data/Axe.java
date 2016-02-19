package data;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Axe extends Weapon {
	private float critChance;

	public Axe(Image image, String name, float xPos, float yPos, int durability, int damage, float critChance) throws SlickException {
		super(image, name, xPos, yPos, durability, damage);
		this.critChance = critChance;
	}

	@Override
	public void attack() {
		// TODO Auto-generated method stub
		
	}

	public float getCritChance() {
		return critChance;
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
