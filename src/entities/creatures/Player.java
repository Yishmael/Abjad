package entities.creatures;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import data.MainGame;

public class Player extends Creature {

	public Player(Image image, float xPos, float yPos) throws SlickException {
		super(image, xPos, yPos);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update() {
			//check key input
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(image, xPos, yPos);
		// TODO Auto-generated method stub
	}

	@Override
	public void move(float x, float y) {
		// can and want to go right
		if (xPos + 64 < MainGame.SCREEN_WIDTH) {
			if (x > 0) {
				xPos += x;
			}
		}
		// can and want to go left
		if (xPos > 0) {
			if (x < 0) {
				xPos += x;
			}
		}
		// can and want to go down
		if (yPos + 72 < MainGame.SCREEN_HEIGHT) {
			if (y > 0) {
				yPos += y;
			}
		}
		// can and want to go up
		if (yPos> 0) {
			if (y < 0) {
				yPos += y;
			}
		}
	}

}
