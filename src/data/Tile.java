package data;

public class Tile {
    private TileType tileType;
    private float x, y;

    public Tile(int x, int y, TileType tileType) {
        // TODO Auto-generated constructor stub
        this.x = x;
        this.y = y;
        this.tileType = tileType;
    }

    public TileType getTileType() {
        return tileType;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setTileType(TileType tileType) {
        this.tileType = tileType;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

}
