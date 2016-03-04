package items.equipable.armor;

import items.Item;

public abstract class Armor extends Item {
    protected float defense;

    public Armor(String name, String iconPath, float defense) {
        super(name, iconPath);
        this.defense = defense;
    }

    public float getDefense() {
        return defense;
    }
}
