package entities.creatures.pcs;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import data.MainGame;
import entities.creatures.Creature;
import entities.items.Item;
import entities.items.weapons.Sword;
import utils.inventory.Inventory;
import utils.map.MapTile;

public class Player extends Creature {

	public Inventory inventory;
	private List<Item> items;
	public MapTile mapTile;
	public boolean showInventory;
	public int tileIndex;

	public Player(Image image, float xPos, float yPos, int health, MapTile mapTile) throws SlickException {
		super(image, xPos, yPos, health);
		this.mapTile = mapTile;
		showInventory = false;
		tileIndex = 1;
		items = new ArrayList<Item>();
		items.add(new Sword(new Image("images/sword.png"), 0, 0, 150, 15));
		inventory = new Inventory(new Image("images/inventory.png"), items);
	}
	
	public boolean inventoryFull() {
		return items.size() == 30;
	}
	
	public void addItem(Item item) {
		items.add(item);
	}

	@Override
	public void move(float x, float y) {
		int xTile = (int) Math.floor((xPos + 70) / 64);
		int yTile = (int) Math.floor((yPos + 64) / 64);
		// can and want to go right
		if (xPos + 64 < MainGame.SCREEN_WIDTH) {
			if (x > 0) {
				if(mapTile.getTileType(xTile, yTile).walkable) {
					xPos += 3*x;
				}
			}
		}
		// can and want to go left
		if (xPos > 0) {
			if (x < 0) {
				xPos += 3*x;
			}
		}
		// can and want to go down
		if (yPos + 72 < MainGame.SCREEN_HEIGHT) {
			if (y > 0) {
				yPos += 3*y;
			}
		}
		// can and want to go up
		if (yPos> 0) {
			if (y < 0) {
				yPos += 3*y;
			}
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(image, xPos, yPos);
	}

	@Override
	public void update() {
		// check key input
	}

}
