package entities.creatures;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import entities.Entity;

public abstract class Creature extends Entity {
	protected int health;

	public Creature(Image image, float xPos, float yPos) throws SlickException {
		super(image, xPos, yPos);
		health = 100;
		// TODO Auto-generated constructor stub
	}
	
	public abstract void move(float x, float y);

}
