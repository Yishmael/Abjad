package data;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Inventory {
	private int height;
	private Image image;
	private int width;
	private int[] matrix;

	public Inventory(Image image, int[] matrix) {
		this.image = image;
		this.width = image.getWidth();
		this.height = image.getHeight();
		this.matrix = matrix;
	}

	public void render(Graphics g) throws SlickException {
		g.drawImage(image, 0, 320, 640, 512, 0, 0, width, height, new Color(255, 255, 255, 200));
		for (int i = 0, j = 0; i < matrix.length; i++) {
			if (i == 10 || i == 20) {
				j++;
			}
			switch (matrix[i]) {
				case 101:
					g.drawImage(new Image("images/axe1.png"), 64 * (i - 10 * j), 320 + 64 * j);
					break;
				case 102:
					g.drawImage(new Image("images/axe2.png"), 64 * (i - 10 * j), 320 + 64 * j);
					break;
				case 201:
					g.drawImage(new Image("images/shield1.png"), 64 * (i - 10 * j), 320 + 64 * j);
					break;
				case 202:
					g.drawImage(new Image("images/shield2.png"), 64 * (i - 10 * j), 320 + 64 * j);
					break;
				case 501:
					g.drawImage(new Image("images/boulder1.png"), 64 * (i - 10 * j), 320 + 64 * j);
					break;
				default:
					break;
			
			}
		}
	}

	public void update() {

	}
}
