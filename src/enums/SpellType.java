package enums;

public enum SpellType {

    Fireball("images/spells/fireball-icon.png", "Fireball", 40, 80, 0, 200, 0.5f),
    Nourish("images/spells/nourish-icon.png", "Nourish", 70, 0, 40, 0, 0.5f),
    Explosion("images/spells/explosion-icon.png", "Explosion", 0, 0, 0, 100, 5f),
    Null("images/null.png", "Null", 0, 0, 0, 0, 0);

    private String iconPath, name;
    private float damage, manaCost, healing, range, cooldown;

    private SpellType(String iconPath, String name, float manaCost, float damage, float healing, float range, float cooldown) {
        this.iconPath = iconPath;
        this.name = name;
        this.manaCost = manaCost;
        this.damage = damage;
        this.healing = healing;
        this.range = range;
        this.cooldown = cooldown;
    }

    public String toString() {
        return name + " (" + (int) manaCost + "/" + (int) damage + "/" + (int) healing + "/" + (int) range + "/"
                + cooldown + ")";
    }
    
    public String getIconPath() {
        return iconPath;
    }

    public String getName() {
        return name;
    }

    public float getDamage() {
        return damage;
    }

    public float getManaCost() {
        return manaCost;
    }

    public float getHealing() {
        return healing;
    }

    public float getRange() {
        return range;
    }

    public float getCooldown() {
        return cooldown;
    }

}
