package items.equipable.weapons;

import items.Item;

public abstract class Weapon extends Item {    
    protected float damage;
    
    public Weapon(String name, String iconPath, float damage) {
        super(name, iconPath);
        this.damage = damage;
    }

    public float getDamage() {
        return damage;
    }

}
