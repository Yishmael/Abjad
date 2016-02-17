package utils.map;

public enum TileType {

	Grass("grass", true), Dirt("dirt", true), Water("water", false), Sand("sand", true), Rock("rock", false);

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
