package utils.map;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class MapTile {
	private final int WIDTH;
	private final int HEIGHT;
	private int[][] matrix ;
//		{{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
//			{ 1, 2, 3, 4, 0, 0, 0, 0, 0, 0 },
//			{ 0, 1, 1, 1, 0, 0, 2, 0, 0, 0 },
//			{ 1, 2, 3, 4, 5, 6, 2, 0, 0, 0 }, 
//			{ 0, 1, 0, 0, 0, 0, 2, 0, 0, 0 },
//			{ 0, 1, 1, 1, 1, 1, 2, 0, 0, 0 }, 
//			{ 0, 1, 0, 0, 0, 1, 0, 0, 0, 0 }, 
//			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }};
	
	public MapTile(final int WIDTH, final int HEIGHT) {
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		matrix = new int[12][12];
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 12 ; j++) {
				matrix[i][j] = 0;
				if (i % 2 == 0 && j % 2 != 0)
					matrix[i][j] = TileType.Water.ordinal();
					if (i == 9)
						matrix[i][j] = TileType.Dirt.ordinal();
					if (j == 7)
						matrix[i][j] = TileType.Sand.ordinal();
					if (j == 7 && i == 9)
						matrix[i][j] = TileType.Rock.ordinal();
			}
		}
	}

	public TileType getTileType(int xTile, int yTile) {
		return TileType.values()[matrix[xTile][yTile]];
	}

	public void setTile(int xTile, int yTile, TileType tileType) {
		matrix[xTile][yTile] = tileType.ordinal();
	}

	public void update() {

	}

	public void render(Graphics g) throws SlickException {
		for (int i = 0; i < WIDTH / 64; i++) {
			for (int j = 0; j < HEIGHT / 64; j++) {
				g.drawImage(getImage(matrix[i][j]), 64 * i + i, 64 * j + j);
			}
		}
	}

	private Image getImage(int tileIndex) throws SlickException {
		Image image;
		String path = "images/" + TileType.values()[tileIndex] + ".png";

		image = new Image(path);

		return image;

	}

}
