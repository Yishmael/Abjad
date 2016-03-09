package enums;

public enum TileType {

    Dirt("images/tiles/dirt.png", true),
    Grass("images/tiles/grass.png", true),
    Sand("images/tiles/sand.png", true),
    Stone("images/tiles/stone.png", false),
    Water("images/tiles/water.png", false),
    Null("images/null.png", false);

    private String imagePath;
    private boolean walkable;

    TileType(String imagePath, boolean walkable) {
        this.imagePath = imagePath;
        this.walkable = walkable;
    };

    public String getImagePath() {
        return imagePath;
    }

    public boolean isWalkable() {
        return walkable;
    }
}
