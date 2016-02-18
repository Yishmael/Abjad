package entities.items;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import entities.Entity;

public abstract class Item extends Entity {
	protected int durability;

	public Item(Image image, int xPos, int yPos, int durability) throws SlickException {
		super(image, xPos, yPos);
		// TODO Auto-generated constructor stub
		this.durability = durability;
	}

	public int getDurability() {
		return durability;
	}

	public void render(Graphics g, int x, int y, int width, int height) {
		g.drawImage(image, x, y, x + width, y + height, 0, 0, image.getWidth(), image.getHeight());
	}

	public void setDurability(int durability) {
		this.durability = durability;
	}
}
