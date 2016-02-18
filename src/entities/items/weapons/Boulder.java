package entities.items.weapons;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Boulder extends Weapon{
	private float speed;
	public Boulder(Image image, int xPos, int yPos, int durability, int damage, float speed) throws SlickException {
		super(image, xPos, yPos, durability, damage);
		this.speed = speed;
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