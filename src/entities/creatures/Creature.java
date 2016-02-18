package entities.creatures;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import entities.Entity;

public abstract class Creature extends Entity {
	protected int health;

	public Creature(Image image, float xPos, float yPos, int health) throws SlickException {
		super(image, xPos, yPos);
		this.health = health;
	}

	public int getHealth() {
		return health;
	}

	public abstract void move(float x, float y);
	
	public void setHealth(int health) {
		this.health = health;
	}

}
