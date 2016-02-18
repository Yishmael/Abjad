package entities.items.weapons;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import entities.items.Item;

public abstract class Weapon extends Item {
	protected int damage;

	public Weapon(Image image, int xPos, int yPos, int durability, int damage) throws SlickException {
		super(image, xPos, yPos, durability);
		// TODO Auto-generated constructor stub
		this.damage = damage;
	}

	public int getDamage() {
		return damage;
	}

}
