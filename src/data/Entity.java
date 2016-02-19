package data;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public abstract class Entity {
	protected Image image;
	protected String name;
	protected float xPos, yPos;
	public boolean visible;

	public Entity(Image image, String name, float xPos, float yPos) throws SlickException {
		this.image = image;
		this.name = name;
		this.xPos = xPos;
		this.yPos = yPos;
		visible = true;
	}
	
	public Image getImage() {
		return image;
	}

	public String getName() {
		return name;
	}

	public float getxPos() {
		return xPos;
	}

	public float getyPos() {
		return yPos;
	}

	public abstract void render(Graphics g);

	public void setxPos(float xPos) {
		this.xPos = xPos;
	}

	public void setyPos(float yPos) {
		this.yPos = yPos;
	}

	public abstract void update(int dt);

}
