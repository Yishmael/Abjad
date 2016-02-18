package data;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import entities.creatures.npcs.Bot;
import entities.creatures.pcs.Player;
import utils.input.KeyHandler;
import utils.map.MapTile;

public class MainGame extends BasicGame {

	public static final int SCREEN_HEIGHT = 512;// 9
	public static final int SCREEN_WIDTH = 640; // 16
	public static final int TILE_HEIGHT = 64;
	public static final int TILE_WIDTH = 64;

	public static void main(String[] args) {
		try {
			AppGameContainer appgc;
			appgc = new AppGameContainer(new MainGame("Stars are falling"));
			appgc.setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, false);
			appgc.setTargetFrameRate(60);
			appgc.start();
		} catch (SlickException ex) {
			Logger.getLogger(MainGame.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private Bot enemy;
	private KeyHandler keyHandler;
	private MapTile mapTile;
	private Player player;

	public MainGame(String gamename) {
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		mapTile = new MapTile(SCREEN_WIDTH, SCREEN_HEIGHT);
		player = new Player(new Image("images/ifrit.png"), SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2, 100, mapTile);
		keyHandler = new KeyHandler(gc, player);
		enemy = new Bot(new Image("images/bahamut.png"), 0, 0, 100, mapTile);
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {

		mapTile.render(g);
		if (player.getyPos() > enemy.getyPos()) {
			enemy.render(g);
			player.render(g);
		} else {
			player.render(g);
			enemy.render(g);
		}

		if (player.showInventory) {
			player.inventory.render(g);
		}
	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException {
		
		keyHandler.update();
		player.update();
		enemy.update();

		if (player.showInventory) {
			player.inventory.update();
		}
	}

}