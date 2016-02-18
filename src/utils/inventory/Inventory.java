package utils.inventory;

import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import entities.items.Item;

public class Inventory {
	private int height;
	private Image image;
	private List<Item> items;
	private int width;

	public Inventory(Image image, List<Item> items) {
		this.image = image;
		this.width = image.getWidth();
		this.height = image.getHeight();
		this.items = items;
	}

	public void render(Graphics g) throws SlickException {
		g.drawImage(image, 0, 320, 640, 512, 0, 0, width, height, new Color(255, 255, 255, 200));
		if (!items.isEmpty()) {
			int j = 0;
			int i = 0;
			for (Item item : items) {
				item.setxPos(i % 10 + 1);
				item.render(g, 64 * i, 320 + 64 * j, 64, 64);
				i++;
				i %= 10;
				if (i % 10 == 0) {
					j++;
					j %= 3;
				}

			}
		}
	}

	public void update() {

	}
}
