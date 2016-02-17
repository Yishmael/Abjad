package data;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import entities.creatures.Bot;
import entities.creatures.Player;
import utils.input.KeyHandler;
import utils.inventory.Inventory;
import utils.map.MapTile;
import utils.map.TileType;

public class MainGame extends BasicGame implements KeyListener {

	public static final int SCREEN_WIDTH = 640; // 16
	public static final int SCREEN_HEIGHT = 512;// 9
	public static final int TILE_WIDTH = 64;
	public static final int TILE_HEIGHT = 64;

	private Player player;
	private Bot enemy;
	private MapTile mapTile;
	private KeyHandler keyHandler;
	private Input input;
	private int speed;
	private int tileIndex;
	private Inventory inventory;
	private boolean showInventory;

	public MainGame(String gamename) {
		super(gamename);
	}

	public static void main(String[] args) {
		try {
			AppGameContainer appgc;
			appgc = new AppGameContainer(new MainGame("The game #1"));
			appgc.setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, false);
			appgc.setTargetFrameRate(60);
			appgc.start();
		} catch (SlickException ex) {
			Logger.getLogger(MainGame.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		mapTile = new MapTile(SCREEN_WIDTH, SCREEN_HEIGHT);
		player = new Player(new Image("images/ifrit.png"), SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);
		keyHandler = new KeyHandler(gc);
		enemy = new Bot(new Image("images/bahamut.png"), 0, 0, mapTile);
		speed = 5;
		tileIndex = 1;
		showInventory = false;
		inventory = new Inventory(new Image("images/null.png"));
	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException {
		input = gc.getInput();
		// TRIBA MAKNIT OVO U KLASU KEYLISTENER
		if (input.isKeyDown(Input.KEY_COMMA)) {
			// move up
			player.move(0, -speed);
		}
		if (input.isKeyDown(Input.KEY_O)) {
			// move down
			player.move(0, speed);
		}
		if (input.isKeyDown(Input.KEY_A)) {
			// move left
			player.move(-speed, 0);
		}
		if (input.isKeyDown(Input.KEY_E)) {
			// move right
			player.move(speed, 0);
		}
		// get current tile name
		if (input.isKeyPressed(Input.KEY_P)) {
			float x = player.getxPos();
			float y = player.getyPos();
			int xTile = (int) Math.floor((x + 32) / 64);
			int yTile = (int) Math.floor((y + 64) / 64);
			System.out.println(mapTile.getTileType(xTile, yTile).name());
		}
		// change brush
		if (input.isKeyPressed(Input.KEY_SPACE)) {
			tileIndex++;
			tileIndex %= TileType.values().length;
			System.out.println("Brush changed to " + TileType.values()[tileIndex]);
		}
		// change tile on mouse location
		if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			float x = input.getMouseX();
			float y = input.getMouseY();
			System.out.println(x + " " + y);
			int xTile = (int) Math.floor((x) / 64);
			int yTile = (int) Math.floor((y) / 64);
			mapTile.setTile(xTile, yTile, TileType.values()[tileIndex]);
		}
		
		if (input.isKeyPressed(Input.KEY_I)) {
			showInventory = !showInventory;
			System.out.println("Inventory:" + showInventory);
		}
		
		if (showInventory) {
			inventory.update();
		}

		keyHandler.update();
		player.update();
		enemy.update();
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		
		mapTile.render(g);
		player.render(g);
		enemy.render(g);
		if (showInventory) {
			inventory.render(g);
		}
	}

	@Override
	public void keyPressed(int key, char c) {
		// System.out.println(c);

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}