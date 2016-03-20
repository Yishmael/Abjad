package enums;

public enum SpellType {

    // sorcerer
    Teleport(
            "images/spells/teleport-icon.png",
            "images/spells/teleport.png",
            "sounds/000.ogg",
            "sounds/000.ogg",
            "Teleport",
            "",
            10,
            0,
            0,
            400,
            0.5f,
            0),
    Fireball(
            "images/spells/fireball-icon.png",
            "images/spells/fireball-1.png",
            "sounds/067.ogg",
            "sounds/068.ogg",
            "Fireball",
            "fire",
            6,
            3,
            0,
            350,
            0.5f,
            7.4f),
    Bouncer(
            "images/spells/bouncer-icon.png",
            "images/spells/bouncer.png",
            "sounds/000.ogg",
            "sounds/020.ogg",
            "Bouncer",
            "arcane",
            4,
            3,
            0,
            450,
            1f,
            6.5f),
    MultiBouncer(
            "images/spells/multibouncer-icon.png",
            "images/spells/multibouncer.png",
            "sounds/000.ogg",
            "sounds/020.ogg",
            "Multi Bouncer",
            "arcane",
            10,
            2,
            0,
            450,
            0.2f,
            5.5f),
    ScorchedEarth(
            "images/spells/scorchedearth-icon.png",
            "images/spells/scorchedearth.png",
            "sounds/067.ogg",
            "sounds/067.ogg",
            "Scorched Earth",
            "fire",
            4,
            3,
            0,
            200,
            4f,
            0),
    CoFireball(
            "images/spells/cofireball-icon.png",
            "images/spells/cofireball-1.png",
            "sounds/067.ogg",
            "sounds/068.ogg",
            "CoFireball",
            "fire",
            10,
            3,
            0,
            400,
            0.4f,
            7.4f),

    // summoner
    ReviveMinion(
            "images/spells/reviveminion-icon.png",
            "images/spells/reviveminion.png",
            "sounds/000.ogg",
            "sounds/000.ogg",
            "Revive Minion",
            "",
            7,
            0,
            0,
            400,
            4,
            0),
    SummonKirith(
            "images/spells/summonkirith-icon.png",
            "images/spells/summonkirith.png",
            "sounds/000.ogg",
            "sounds/000.ogg",
            "Summon Kirith",
            "",
            15,
            2,
            0,
            0,
            1f,
            0),
    SummonWall(
            "images/spells/summonwall-icon.png",
            "images/spells/summonwall.png",
            "sounds/000.ogg",
            "sounds/000.ogg",
            "Summon Wall",
            "",
            15,
            0,
            0,
            0,
            1f,
            0),

    // protector
    Nourish(
            "images/spells/nourish-icon.png",
            "images/spells/nourish.png",
            "sounds/049.ogg",
            "sounds/000.ogg",
            "Nourish",
            "",
            12,
            0,
            5,
            0,
            0.5f,
            0),
    HolyShield(
            "images/spells/holyshield-icon.png",
            "images/spells/holyshield.png",
            "sounds/000.ogg",
            "sounds/000.ogg",
            "Holy Shield",
            "holy",
            20,
            199,
            0,
            0,
            1f,
            0),
    
    // disabler
    Weaken(
            "images/spells/weaken-icon.png",
            "images/spells/weaken.png",
            "sounds/000.ogg",
            "sounds/000.ogg",
            "Weaken",
            "",
            10,
            25,
            0,
            300,
            0.66f,
            0),
    Decrepify(
            "images/spells/decrepify-icon.png",
            "images/spells/decrepify.png",
            "sounds/000.ogg",
            "sounds/000.ogg",
            "Decrepify",
            "",
            10,
            25,
            0,
            300,
            0.34f,
            0),
    PoisonArrow(
            "images/spells/poisonarrow-icon.png",
            "images/spells/poisonarrow.png",
            "sounds/024.ogg",
            "sounds/000.ogg",
            "Poison Arrow",
            "poison",
            5,
            3,
            0,
            250,
            0.34f,
            9.5f),
    Null(
            "images/spells/null-icon.png",
            "images/null.png",
            "sounds/000.ogg",
            "sounds/000.ogg",
            "Null",
            "",
            0,
            0,
            0,
            0,
            0,
            0);

    private String iconPath, imagePath, soundPath, deathSoundPath, name, damageType;
    private float damage, manaCost, healing, range, cooldown, speed;

    private SpellType(String iconPath, String imagePath, String soundPath, String deathSoundPath, String name, String damageType,
            float manaCost, float damage, float healing, float range, float cooldown, float speed) {
        this.iconPath = iconPath;
        this.imagePath = imagePath;
        this.soundPath = soundPath;
        this.deathSoundPath = deathSoundPath;
        this.name = name;
        this.damageType = damageType;
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

    public String getDamageType() {
        return damageType;
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
