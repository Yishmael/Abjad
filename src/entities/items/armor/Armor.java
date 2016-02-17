package entities.items.armor;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import entities.items.Item;

public abstract class Armor extends Item {
	protected int defense;

	public Armor(Image image, int xPos, int yPos) throws SlickException {
		super(image, xPos, yPos);
		// TODO Auto-generated constructor stub
	}

}
