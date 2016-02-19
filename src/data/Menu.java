package data;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Menu {

	private Image image;

	public Menu(Image image) {
		this.image = image;
		// TODO Auto-generated constructor stub
	}

	public void render(Graphics g) throws SlickException {
		g.drawImage(image, 130, 130, 450, 320, 0, 0, image.getWidth(), image.getHeight());
	}

	public void update() {

	}
}
