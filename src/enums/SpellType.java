package enums;

public enum SpellType {

    // sorcerer
    Teleport(
            "res/images/spells/teleport-icon.png",
            "res/images/spells/teleport.png",
            "res/sounds/000.ogg",
            "res/sounds/000.ogg",
            "Teleport",
            "",
            10,
            0,
            0,
            400,
            0.5f,
            0),
    Fireball(
            "res/images/spells/fireball-icon.png",
            "res/images/spells/fireball-1.png",
            "res/sounds/067.ogg",
            "res/sounds/068.ogg",
            "Fireball",
            "fire",
            6,
            3,
            0,
            350,
            0.5f,
            7.4f),
    Bouncer(
            "res/images/spells/bouncer-icon.png",
            "res/images/spells/bouncer.png",
            "res/sounds/000.ogg",
            "res/sounds/020.ogg",
            "Bouncer",
            "arcane",
            4,
            3,
            0,
            450,
            1f,
            6.5f),
    MultiBouncer(
            "res/images/spells/multibouncer-icon.png",
            "res/images/spells/multibouncer.png",
            "res/sounds/000.ogg",
            "res/sounds/020.ogg",
            "Multi Bouncer",
            "arcane",
            10,
            2,
            0,
            450,
            0.2f,
            5.5f),
    ScorchedEarth(
            "res/images/spells/scorchedearth-icon.png",
            "res/images/spells/scorchedearth.png",
            "res/sounds/067.ogg",
            "res/sounds/067.ogg",
            "Scorched Earth",
            "fire",
            4,
            1,
            0,
            200,
            4f,
            0),
    CoFireball(
            "res/images/spells/cofireball-icon.png",
            "res/images/spells/cofireball-1.png",
            "res/sounds/067.ogg",
            "res/sounds/068.ogg",
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
            "res/images/spells/reviveminion-icon.png",
            "res/images/spells/reviveminion.png",
            "res/sounds/000.ogg",
            "res/sounds/000.ogg",
            "Revive Minion",
            "",
            7,
            0,
            0,
            400,
            4,
            0),
    SummonKirith(
            "res/images/spells/summonkirith-icon.png",
            "res/images/spells/summonkirith.png",
            "res/sounds/000.ogg",
            "res/sounds/000.ogg",
            "Summon Kirith",
            "",
            15,
            2,
            0,
            0,
            1f,
            0),
    SummonWall(
            "res/images/spells/summonwall-icon.png",
            "res/images/spells/summonwall.png",
            "res/sounds/000.ogg",
            "res/sounds/000.ogg",
            "Summon Wall",
            "",
            15,
            0,
            0,
            0,
            1f,
            0),

    // protector
    Heal(
            "res/images/spells/heal-icon.png",
            "res/images/spells/heal.png",
            "res/sounds/049.ogg",
            "res/sounds/000.ogg",
            "Heal",
            "",
            12,
            0,
            10,
            0,
            0.5f,
            0),
    HolyShield(
            "res/images/spells/holyshield-icon.png",
            "res/images/spells/holyshield.png",
            "res/sounds/000.ogg",
            "res/sounds/000.ogg",
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
            "res/images/spells/weaken-icon.png",
            "res/images/spells/weaken.png",
            "res/sounds/000.ogg",
            "res/sounds/000.ogg",
            "Weaken",
            "",
            10,
            25,
            0,
            300,
            0.66f,
            0),
    Decrepify(
            "res/images/spells/decrepify-icon.png",
            "res/images/spells/decrepify.png",
            "res/sounds/000.ogg",
            "res/sounds/000.ogg",
            "Decrepify",
            "",
            10,
            25,
            0,
            300,
            0.34f,
            0),
    PoisonArrow(
            "res/images/spells/poisonarrow-icon.png",
            "res/images/spells/poisonarrow.png",
            "res/sounds/024.ogg",
            "res/sounds/000.ogg",
            "Poison Arrow",
            "poison",
            5,
            0,
            0,
            150,
            0.34f,
            9.5f),
    Null(
            "res/images/spells/null-icon.png",
            "res/images/null.png",
            "res/sounds/000.ogg",
            "res/sounds/000.ogg",
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

    private SpellType(String iconPath, String imagePath, String soundPath, String deathSoundPath, String name,
            String damageType, float manaCost, float damage, float healing, float range, float cooldown, float speed) {
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

    public static SpellType[] getSorcererSpells() {
        return new SpellType[] { SpellType.Fireball, SpellType.Bouncer, SpellType.MultiBouncer, SpellType.CoFireball,
                SpellType.ScorchedEarth, SpellType.Teleport };
    }

    public static SpellType[] getSummonerSpells() {
        return new SpellType[] { SpellType.SummonWall, SpellType.ReviveMinion, SpellType.SummonKirith };
    }

    public static SpellType[] getProtectorSpells() {
        return new SpellType[] { SpellType.Heal, SpellType.HolyShield };
    }

    public static SpellType[] getDisablerSpells() {
        return new SpellType[] { SpellType.PoisonArrow, SpellType.Weaken, SpellType.Decrepify };
    }

}
