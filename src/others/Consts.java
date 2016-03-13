package others;

public final class Consts {
    public static final int TILE_SIZE = 64;

    public static final int SCREEN_WIDTH = TILE_SIZE * 10; // 5 : 4
    public static final int SCREEN_HEIGHT = TILE_SIZE * 8;
    public static final boolean FULLSCREEN = false;

    // public static final int SCREEN_WIDTH = 1366; // 16 : 9
    // public static final int SCREEN_HEIGHT = 768;
    // public static final boolean FULLSCREEN = true;

    public static final int FPS = 2000;

    // TODO make them enums
    // bit 0 reserved
    public static final int COMBAT = 0x00000002;
    public static final int DEFEND = 0x00000004;
    public static final int INPUT = 0x00000008;
    public static final int INVENTORY = 0x00000010;
    public static final int TRANSFORM = 0x00000020;
    public static final int SPRITE = 0x00000040;
    public static final int PLAYERMOVEMENT = 0x00000080;
    public static final int HEALTH = 0x00000100;
    public static final int MANA = 0x00000200;
    public static final int COLLISION = 0x00000400;
    public static final int SPELL = 0x00000800;
    public static final int CAMERA = 0x00001000;
    public static final int ATTRIBUTES = 0x00002000;
    public static final int LEVEL = 0x00004000;
    public static final int STATUS = 0x00008000;
    public static final int COMPUTERMOVEMENT = 0x00010000;
    public static final int YIELD = 0x00020000;
    public static final int IMPACT = 0x00040000;
    public static final int GUIDE = 0x00080000;
    public static final int SPLIT = 0x00100000;

    public Consts() {
        throw new AssertionError("Can't instantiate this object!");
    }
}
