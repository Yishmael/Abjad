package enums;

public enum SpellType {

    CoFireball(
            "images/spells/cofireball-icon.png",
            "images/spells/cofireball.png",
            "sounds/067.ogg",
            "sounds/068.ogg",
            "CoFireball",
            10,
            50,
            0,
            200,
            0.4f,
            7.4f),
    Fireball(
            "images/spells/fireball-icon.png",
            "images/spells/fireball.png",
            "sounds/067.ogg",
            "sounds/068.ogg",
            "Fireball",
            30,
            50,
            0,
            200,
            0.5f,
            7.4f),
    Bouncer(
            "images/spells/bouncer-icon.png",
            "images/spells/bouncer.png",
            "sounds/000.ogg",
            "sounds/020.ogg",
            "Bouncer",
            20,
            20,
            0,
            200,
            1f,
            6.5f),
    SummonKirith(
            "images/spells/summonkirith-icon.png",
            "images/spells/summonkirith.png",
            "sounds/000.ogg",
            "sounds/000.ogg",
            "Summon Kirith",
            80,
            25,
            0,
            0,
            1f,
            0),
    HolyShield(
            "images/spells/holyshield-icon.png",
            "images/spells/holyshield.png",
            "sounds/000.ogg",
            "sounds/000.ogg",
            "Holy Shield",
            30,
            199,
            0,
            0,
            1f,
            0),
    MultiBouncer(
            "images/spells/multibouncer-icon.png",
            "images/spells/multibouncer.png",
            "sounds/000.ogg",
            "sounds/020.ogg",
            "Multi Bouncer",
            50,
            10,
            0,
            200,
            2f,
            6.5f),
    Weaken(
            "images/spells/weaken-icon.png",
            "images/spells/weaken.png",
            "sounds/000.ogg",
            "sounds/000.ogg",
            "Weaken",
            10,
            25,
            0,
            0,
            0.66f,
            0),
    Decrepify(
            "images/spells/decrepify-icon.png",
            "images/spells/decrepify.png",
            "sounds/000.ogg",
            "sounds/000.ogg",
            "Decrepify",
            10,
            25,
            0,
            0,
            0.34f,
            0),
    PoisonArrow(
            "images/spells/poisonarrow-icon.png",
            "images/spells/poisonarrow.png",
            "sounds/024.ogg",
            "sounds/000.ogg",
            "Poison Arrow",
            10,
            7,
            0,
            200,
            0.34f,
            9.5f),
    Nourish(
            "images/spells/nourish-icon.png",
            "images/spells/nourish.png",
            "sounds/049.ogg",
            "sounds/000.ogg",
            "Nourish",
            60,
            0,
            20,
            0,
            0.5f,
            0),
    ScorchedEarth(
            "images/spells/scorchedearth-icon.png",
            "images/spells/scorchedearth.png",
            "sounds/067.ogg",
            "sounds/067.ogg",
            "Scorched Earth",
            20,
            25,
            0,
            0,
            4f,
            0),

    // Explosion("images/spells/explosion-icon.png", "sounds/aa.ogg",
    // "Explosion", 0, 0, 0, 100, 5f),
    Null(
            "images/spells/null-icon.png",
            "images/null.png",
            "sounds/000.ogg",
            "sounds/000.ogg",
            "Null",
            0,
            0,
            0,
            0,
            0,
            0);

    private String iconPath, imagePath, soundPath, deathSoundPath, name;
    private float damage, manaCost, healing, range, cooldown, speed;

    private SpellType(String iconPath, String imagePath, String soundPath, String deathSoundPath, String name,
            float manaCost, float damage, float healing, float range, float cooldown, float speed) {
        this.iconPath = iconPath;
        this.imagePath = imagePath;
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

    public String getImagePath() {
        return imagePath;
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
