package data;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public abstract class Weapon extends Item {
	protected int damage;

	public Weapon(Image image, String name, float xPos, float yPos, int durability, int damage) throws SlickException {
		super(image, name, xPos, yPos, durability);
		// TODO Auto-generated constructor stub
		this.damage = damage;
	}

	public abstract void attack();
	
	public int getDamage() {
		return damage;
	}

}
