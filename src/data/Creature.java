package data;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public abstract class Creature extends Entity {
	protected int health;
	protected MapTile mapTile;
	protected float speed;
	protected float delta = 0f;

	public Creature(Image image, String name, float xPos, float yPos, int health, float speed, MapTile mapTile) throws SlickException {
		super(image, name, xPos, yPos);
		this.health = health;
		this.speed = speed;
		this.mapTile = mapTile;
	}

	public int getHealth() {
		return health;
	}

	public MapTile getMapTile() {
		return mapTile;
	}

	public float getSpeed() {
		return speed;
	}

	public abstract void move(float x, float y);

	public void setHealth(int health) {
		this.health = health;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

}
