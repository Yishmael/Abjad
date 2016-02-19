package data;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public abstract class Armor extends Item {
	protected int defense;

	public Armor(Image image, String name, float xPos, float yPos, int durability, int defense) throws SlickException {
		super(image, name, xPos, yPos, durability);
		this.defense = defense;
	}

	public int getDefense() {
		return defense;
	}

}
