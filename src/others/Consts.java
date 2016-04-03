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

    // units
    public static final long ATTACK = 0x00000002L;
    public static final long DEFENSE = 0x00000004L;
    public static final long INPUT = 0x00000008L;
    public static final long INVENTORY = 0x00000010L;
    public static final long TRANSFORM = 0x00000020L;
    public static final long SPRITE = 0x00000040L;
    public static final long PLAYERMOVEMENT = 0x00000080L;
    public static final long HEALTH = 0x00000100L;
    public static final long MANA = 0x00000200L;
    public static final long COLLISION = 0x00000400L;
    public static final long CAST = 0x00000800L;
    public static final long CAMERA = 0x00001000L;
    public static final long ATTRIBUTES = 0x00002000L;
    public static final long LEVEL = 0x00004000L;
    public static final long STATUS = 0x00008000L;
    public static final long COMPUTERMOVEMENT = 0x00010000L;
    public static final long YIELD = 0x00020000L;
    public static final long RESISTANCE = 0x10000000L;

    // spells
    public static final long SPELL = 0x00040000L;
    public static final long GUIDE = 0x00080000L;
    public static final long SUMMON = 0x00100000L;
    public static final long AREAOFEFFECT = 0x04000000L;
    public static final long SPELLDAMAGE = 0x20000000L;
    public static final long DURATION = 0x80000000L;
    public static final long LINEARMOVEMENT = 0x800000000L;
    public static final long CIRCULARMOVEMENT = 0x1000000000L;
    public static final long SPELLPERIODICDAMAGE = 0x2000000000L;
    public static final long SPELLHEAL = 0x4000000000L;
    public static final long BOUNCE = 0x8000000000L;

    // items
    public static final long WEAPON = 0x00200000L;
    public static final long ARMOR = 0x00400000L;
    public static final long HEALTHBONUS = 0x00800000L;
    public static final long MANABONUS = 0x01000000L;
    public static final long MOVEMENTBONUS = 0x02000000L;
    public static final long CONSUMABLE = 0x40000000L;
    public static final long LIFESTEAL = 0x100000000L;
    public static final long ATTRIBUTESBONUS = 0x200000000L;
    public static final long RESISTANCEBONUS = 0x400000000L;
    public static final long ELEMENTALATTACKDAMAGEBONUS = 0x10000000000L;

    public Consts() {
        throw new AssertionError("Can't instantiate this object!");
    }
}
