package entities.items.weapons;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import entities.items.Item;

public abstract class Weapon extends Item {
	protected int damage;

	public Weapon(Image image, int xPos, int yPos) throws SlickException {
		super(image, xPos, yPos);
		// TODO Auto-generated constructor stub
	}

}
