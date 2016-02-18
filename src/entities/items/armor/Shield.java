package entities.items.armor;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Shield extends Armor {

	private float reflect;

	public Shield(Image image, int xPos, int yPos, int durability, int defense, float reflect) throws SlickException {
		super(image, xPos, yPos, durability, defense);
		this.reflect = reflect;
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}


}
