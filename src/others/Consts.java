package others;

public final class Consts {

    public static final int SCREEN_WIDTH = 640; // 16 : 9
    public static final int SCREEN_HEIGHT = 512;

    public static final int TILE_SIZE = 64;

    // TODO make them enums
    public static final int COMBAT = 1;
    public static final int DEFEND = 2;
    public static final int INPUT = 3;
    public static final int INVENTORY = 4;
    public static final int TRANSFORM = 5;
    public static final int SPRITE = 6;
    public static final int MOVEMENT = 7;
    public static final int HEALTH = 8;
    public static final int MANA = 9;
    public static final int COLLISION = 10;
    public static final int SPELL = 11;
    public static final int CAMERA = 12;
    public static final int ATTRIBUTES = 13;
    public static final int LEVEL = 14;
    
    public Consts() {
        throw new AssertionError("Can't instantiate this object!");
    }
}
