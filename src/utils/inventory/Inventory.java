package utils.inventory;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Inventory {
	Image image;

	public Inventory(Image image) {
		this.image = image;
	}
	
	public void update() {
		
	}
	
	public void render(Graphics g) {
		g.drawImage(image, 0, 320, 640, 512, 0, 0, 64, 64, new Color(255, 100, 200));
	}
}
