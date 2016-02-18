package utils.map;

import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class MapTile {
	private int[][] matrix; 
//		{{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
//			{ 1, 2, 3, 4, 0, 0, 0, 0, 0, 0 },
//			{ 1, 2, 3, 4, 0, 0, 0, 0, 0, 0 },
//			{ 1, 2, 3, 4, 0, 0, 0, 0, 0, 0 },
//			{ 1, 2, 3, 4, 0, 0, 0, 0, 0, 0 },
//			{ 1, 2, 3, 4, 0, 0, 0, 0, 0, 0 },
//			{ 0, 1, 1, 1, 0, 0, 2, 0, 0, 0 },
//			{ 1, 2, 3, 4, 5, 6, 2, 0, 0, 0 }, 
//			{ 0, 1, 0, 0, 0, 0, 2, 0, 0, 0 },
//			{ 0, 1, 1, 1, 1, 1, 2, 0, 0, 0 }, 
//			{ 0, 1, 0, 0, 0, 1, 0, 0, 0, 0 }, 
//			{ 0, 1, 0, 0, 0, 1, 0, 0, 0, 0 }, 
//			{ 0, 1, 0, 0, 0, 1, 0, 0, 0, 0 }, 
//			{ 0, 1, 0, 0, 0, 1, 0, 0, 0, 0 }, 
//			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }};
	private int xMax, yMax;
	
	public MapTile(final int WIDTH, final int HEIGHT) {
		this.xMax = WIDTH/64;
		this.yMax = HEIGHT/64;
		Random dice = new Random();
		matrix = new int[xMax][yMax];
		for (int i = 0; i < xMax; i++) {
			for (int j = 0; j < yMax ; j++) {
				int rand = dice.nextInt(100);
				if (rand < 66)
					matrix[i][j] = TileType.Grass.ordinal();
				else if (rand >= 66 && rand < 95)
					matrix[i][j] = TileType.Dirt.ordinal();
				else
					matrix[i][j] = TileType.Rock.ordinal();
			}
		}
	}

	private Image getImage(int tileIndex) throws SlickException {
		Image image;
		String path = "images/" + TileType.values()[tileIndex] + ".png";

		image = new Image(path);

		return image;

	}

	public TileType getTileType(int xTile, int yTile) {
		return TileType.values()[matrix[xTile][yTile]];
	}

	public void render(Graphics g) throws SlickException {
		for (int i = 0; i < xMax; i++) {
			for (int j = 0; j < yMax; j++) {
				g.drawImage(getImage(matrix[i][j]), 64 * i + i, 64 * j + j);
			}
		}
	}

	public void setTile(int xTile, int yTile, TileType tileType) {
		matrix[xTile][yTile] = tileType.ordinal();
	}

	public void update() {

	}

}
