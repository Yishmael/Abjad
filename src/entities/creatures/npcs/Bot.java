package entities.creatures.npcs;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import data.MainGame;
import entities.creatures.Creature;
import utils.map.MapTile;

public class Bot extends Creature {
	private MapTile mapTile;

	public Bot(Image image, int xPos, int yPos, int health, MapTile mapTile) throws SlickException {
		super(image, xPos, yPos, health);
		// TODO Auto-generated constructor stub
		this.mapTile = mapTile;
	}

	@Override
	public void move(float x, float y) {
		// TODO Auto-generated method stub
		xPos += x;
		yPos += y;
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(image, xPos, yPos);
		// TODO Auto-generated method stub

	}

	@Override
	public void update() {
		int xTile = (int) Math.floor((xPos + 70) / 64);
		int yTile = (int) Math.floor((yPos + 64) / 64);

		// can right and wants right
		if (xPos + 90 < MainGame.SCREEN_WIDTH) {
			
			if (mapTile.getTileType(xTile, yTile).walkable)
				xPos += 3;
		}
		xPos %= MainGame.SCREEN_WIDTH - 90;
		yPos %= MainGame.SCREEN_HEIGHT - 90;

	}

}
