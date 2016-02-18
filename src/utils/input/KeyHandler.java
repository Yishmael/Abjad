package utils.input;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import entities.creatures.pcs.Player;
import entities.items.weapons.Axe;
import utils.map.TileType;

public class KeyHandler{
	private GameContainer gc;
	private Input input;
	private Player player;

	public KeyHandler(GameContainer gc, Player player) {
		this.gc = gc;
		this.player = player;
	}

	public void update() throws SlickException {
		input = gc.getInput();
		// TRIBA MAKNIT OVO U KLASU KEYLISTENER
		if (input.isKeyDown(Input.KEY_COMMA)) {
			// move up
			player.move(0, -1);
		}
		if (input.isKeyDown(Input.KEY_O)) {
			// move down
			player.move(0, 1);
		}
		if (input.isKeyDown(Input.KEY_A)) {
			// move left
			player.move(-1, 0);
		}
		if (input.isKeyDown(Input.KEY_E)) {
			// move right
			player.move(1, 0);
		}
		// get current tile name
		if (input.isKeyPressed(Input.KEY_P)) {
			float x = player.getxPos();
			float y = player.getyPos();
			int xTile = (int) Math.floor((x + 32) / 64);
			int yTile = (int) Math.floor((y + 64) / 64);
			System.out.println(player.mapTile.getTileType(xTile, yTile).name());
		}
		if (input.isKeyPressed(Input.KEY_SPACE)) {
			if (!player.inventoryFull()) {
				player.addItem(new Axe(new Image("images/axe.png"), 0, 0, 100, 10, 0.7f));
			} else {
				System.out.println("Inventory full!");
			}
		}
		// change brush
		if (input.isKeyPressed(Input.KEY_E)) {
			player.tileIndex++;
			player.tileIndex %= TileType.values().length;
			System.out.println("Brush changed to " + TileType.values()[player.tileIndex]);
		}
		// change tile on mouse location
		if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			float x = input.getMouseX();
			float y = input.getMouseY();
			System.out.println(x + " " + y);
			int xTile = (int) Math.floor((x) / 64);
			int yTile = (int) Math.floor((y) / 64);
			player.mapTile.setTile(xTile, yTile, TileType.values()[player.tileIndex]);
		}

		if (input.isKeyPressed(Input.KEY_I)) {
			player.showInventory = !player.showInventory;
			System.out.println("Inventory " + (player.showInventory ? "on" : "off"));
		}

	}
	
	

}
