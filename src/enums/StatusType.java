package enums;

public enum StatusType {
    // buffs
    Fury("AS", 1),
    Haste("MS", 1),
    Rage("DMG", 1),
    HoT("HPregen", 1),
    MoT("MPregen", 1),

    // curses
    Decrepify("HPcap", -1),
    Weaken("DMG", -1),

    // debuffs
    DoT("HPregen", -1);

    private String effect;
    private int sign;

    StatusType(String effect, int sign) {
        this.effect = effect;
        this.sign = sign;
    }

    public String getEffect() {
        return effect;
    }

    public int getSign() {
        return sign;
    }

    public static boolean exists(String effect) {
        for (StatusType type: values()) {
            if (type.name().equals(effect)) {
                return true;
            }
        }
        return false;
    }
}
