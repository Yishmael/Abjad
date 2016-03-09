package enums;

public enum SpellType {

    Fireball(
            "images/spells/fireball-icon.png",
            "sounds/067.wav",
            "sounds/068.wav",
            "Fireball",
            30,
            50,
            0,
            200,
            0.5f,
            3.7f),
    Nourish("images/spells/nourish-icon.png", "sounds/049.wav", "sounds/000.ogg", "Nourish", 60, 0, 20, 0, 0.5f, 0),
    BurningGround(
            "images/spells/burningground-icon.png",
            "sounds/067.wav",
            "sounds/067.wav",
            "Burning Ground",
            0,
            0,
            0,
            0,
            1,
            0),

    // Explosion("images/spells/explosion-icon.png", "sounds/aa.ogg",
    // "Explosion", 0, 0, 0, 100, 5f),
    Null("images/null.png", "sounds/000.ogg", "sounds/000.ogg", "Null", 0, 0, 0, 0, 0, 0);

    private String iconPath, soundPath, deathSoundPath, name;
    private float damage, manaCost, healing, range, cooldown, speed;

    private SpellType(String iconPath, String soundPath, String deathSoundPath, String name, float manaCost,
            float damage, float healing, float range, float cooldown, float speed) {
        this.iconPath = iconPath;
        this.soundPath = soundPath;
        this.deathSoundPath = deathSoundPath;
        this.name = name;
        this.manaCost = manaCost;
        this.damage = damage;
        this.healing = healing;
        this.range = range;
        this.cooldown = cooldown;
        this.speed = speed;
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

    public String getDeathSoundPath() {
        return deathSoundPath;
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

    public float getSpeed() {
        return speed;
    }

}
