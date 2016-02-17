package entities;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public abstract class Entity {
	protected Image image;
	protected float xPos, yPos;

	public Entity(Image image, float xPos, float yPos) throws SlickException {
		this.image = image;
		this.xPos = xPos;
		this.yPos = yPos;
	}

	public float getxPos() {
		return xPos;
	}

	public void setxPos(float xPos) {
		this.xPos = xPos;
	}

	public float getyPos() {
		return yPos;
	}

	public void setyPos(float yPos) {
		this.yPos = yPos;
	}

	public abstract void update();

	public abstract void render(Graphics g);

}
