package components;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class SpriteComponent implements Component {
	private String imagePath;
	private String name;
	private Image image;
	private Graphics g;

	public SpriteComponent(String imagePath, String name) throws SlickException {

		this.imagePath = imagePath;
		this.name = name;
		image = new Image(imagePath);
		g = new Graphics();
	}

	public String getName() {
		return name;
	}
	
	public void update() {
		g.drawImage(image, 0, 0, 333, 333, 0, 0, 64, 64);
	}
}
