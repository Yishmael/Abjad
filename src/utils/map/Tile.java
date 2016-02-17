package utils.map;

public class Tile {
	private float x, y;
	private TileType tileType;
	
	public Tile(int x, int y, TileType tileType) {
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = y;
		this.tileType = tileType;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public TileType getTileType() {
		return tileType;
	}

	public void setTileType(TileType tileType) {
		this.tileType = tileType;
	}
	
	
}
