package utils.map;

public enum TileType {

	Dirt("dirt", true), Grass("grass", true), Rock("rock", false), Sand("sand", true), Water("water", false);

	private String tileName;
	public boolean walkable;

	TileType(String tileName, boolean walkable) {
		this.tileName = tileName;
		this.walkable = walkable;
	};

	public boolean isWalkable(TileType tileType) {
		return tileType.walkable;
	}
}
