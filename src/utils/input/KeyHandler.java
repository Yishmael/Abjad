package utils.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

public class KeyHandler implements KeyListener {
	private Input input;
	private GameContainer gc;

	public KeyHandler(GameContainer gc) {
		this.gc = gc;
	}

	public void update() {
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
