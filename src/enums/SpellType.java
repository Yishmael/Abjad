package enums;

public enum SpellType {

    Fireball("images/spells/fireball-icon.png", "sounds/067.wav", "Fireball", 30, 50, 0, 200, 0.5f),
    Nourish("images/spells/nourish-icon.png", "sounds/049.wav", "Nourish", 60, 0, 20, 0, 0.5f),
    // Explosion("images/spells/explosion-icon.png", "sounds/aa.ogg",
    // "Explosion", 0, 0, 0, 100, 5f),
    Null("images/null.png", "sounds/aa.ogg", "Null", 0, 0, 0, 0, 0);

    private String iconPath, soundPath, name;
    private float damage, manaCost, healing, range, cooldown;

    private SpellType(String iconPath, String soundPath, String name, float manaCost, float damage, float healing,
            float range, float cooldown) {
        this.iconPath = iconPath;
        this.soundPath = soundPath;
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

    public String getSoundPath() {
        return soundPath;
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
