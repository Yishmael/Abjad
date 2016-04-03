package enums;

public enum SpellType {

    // sorcerer
    Teleport(
            "res/images/spells/teleport-icon.png",
            "res/images/spells/teleport.png",
            "res/sounds/000.ogg",
            "res/sounds/000.ogg",
            "Teleport"),
    Fireball(
            "res/images/spells/fireball-icon.png",
            "res/images/spells/fireball-1.png",
            "res/sounds/067.ogg",
            "res/sounds/068.ogg",
            "Fireball"),
    Bouncer(
            "res/images/spells/bouncer-icon.png",
            "res/images/spells/bouncer.png",
            "res/sounds/000.ogg",
            "res/sounds/020.ogg",
            "Bouncer"),
    MultiBouncer(
            "res/images/spells/multibouncer-icon.png",
            "res/images/spells/multibouncer.png",
            "res/sounds/000.ogg",
            "res/sounds/020.ogg",
            "Multi Bouncer"),
    ScorchedEarth(
            "res/images/spells/scorchedearth-icon.png",
            "res/images/spells/scorchedearth.png",
            "res/sounds/067.ogg",
            "res/sounds/067.ogg",
            "Scorched Earth"),
    // summoner
    ReviveMinion(
            "res/images/spells/reviveminion-icon.png",
            "res/images/spells/reviveminion.png",
            "res/sounds/000.ogg",
            "res/sounds/000.ogg",
            "Revive Minion"),
    SummonKirith(
            "res/images/spells/summonkirith-icon.png",
            "res/images/spells/summonkirith.png",
            "res/sounds/000.ogg",
            "res/sounds/000.ogg",
            "Summon Kirith"),
    SummonWall(
            "res/images/spells/summonwall-icon.png",
            "res/images/spells/summonwall.png",
            "res/sounds/000.ogg",
            "res/sounds/000.ogg",
            "Summon Wall"),
    // protector
    Heal(
            "res/images/spells/heal-icon.png",
            "res/images/spells/heal.png",
            "res/sounds/049.ogg",
            "res/sounds/000.ogg",
            "Heal"),
    HolyShield(
            "res/images/spells/holyshield-icon.png",
            "res/images/spells/holyshield.png",
            "res/sounds/000.ogg",
            "res/sounds/000.ogg",
            "Holy Shield"),
    // disabler
    Weaken(
            "res/images/spells/weaken-icon.png",
            "res/images/spells/weaken.png",
            "res/sounds/000.ogg",
            "res/sounds/000.ogg",
            "Weaken"),
    Decrepify(
            "res/images/spells/decrepify-icon.png",
            "res/images/spells/decrepify.png",
            "res/sounds/000.ogg",
            "res/sounds/000.ogg",
            "Decrepify"),
    PoisonArrow(
            "res/images/spells/poisonarrow-icon.png",
            "res/images/spells/poisonarrow.png",
            "res/sounds/024.ogg",
            "res/sounds/000.ogg",
            "Poison Arrow"),
    Null("res/images/spells/null-icon.png", "res/images/null.png", "res/sounds/000.ogg", "res/sounds/000.ogg", "Null");

    private String iconPath, imagePath, soundPath, deathSoundPath, name;

    private SpellType(String iconPath, String imagePath, String soundPath, String deathSoundPath, String name) {
        this.iconPath = iconPath;
        this.imagePath = imagePath;
        this.soundPath = soundPath;
        this.deathSoundPath = deathSoundPath;
        this.name = name;
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

    public static SpellType[] getSorcererSpells() {
        return new SpellType[] { SpellType.Fireball, SpellType.Bouncer, SpellType.MultiBouncer, SpellType.ScorchedEarth,
                SpellType.Teleport };
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
