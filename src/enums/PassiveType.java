package enums;

public enum PassiveType {

    Strength("images/passives/stats/strength.png", "Strength", "STR++"),
    Agility("images/passives/stats/agility.png", "Agility", "AGI++"),
    Intelligence("images/passives/stats/intelligence.png", "Intelligence", "INT++"),

    Cleave("images/passives/cleave.png", "+7 Cleave radius", "CLV 7%"),
    LifeSteal("images/passives/lifesteal.png", "+5% Life steal", "LS 5%"),
    Damage("images/passives/damage.png", "+5% Damage", "DMG 5%"),

    Critical("images/passives/critical.png", "+5% Critical", "CRT 5%"),
    MoveSpeed("images/passives/movespeed.png", "+2% Movement speed", "MS 2%"),
    AttackSpeed("images/passives/attackspeed.png", "+5% Attack speed", "AS 5%"),

    SpellPower("images/passives/spellpower.png", "+5% Spell power", "SP 5%"),
    ManaCap("images/passives/manacap.png", "+50 Mana capacity", "MPcap 50"),
    ManaRegen("images/passives/manaregen.png", "+3 Mana regeneration", "MPregen 3"),

    CoFireball("images/spells/cofireball-icon.png", "CoFireball", "learn " + SpellType.CoFireball.ordinal()),
    Fireball("images/spells/fireball-icon.png", "Fireball", "learn " + SpellType.Fireball.ordinal()),
    ScorchedEarth(
            "images/spells/scorchedearth-icon.png",
            "Scorched Earth",
            "learn " + SpellType.ScorchedEarth.ordinal()),

    ReviveMinion("images/spells/reviveminion-icon.png", "Revive Minion", "learn " + SpellType.ReviveMinion.ordinal()),
    SummonKirith("images/spells/summonkirith-icon.png", "Summon Kirith", "learn " + SpellType.SummonKirith.ordinal()),
    SummonWall("images/spells/summonwall-icon.png", "Summon Wall", "learn " + SpellType.SummonWall.ordinal()),

    Teleport("images/spells/teleport-icon.png", "Teleport", "learn " + SpellType.Teleport.ordinal()),
    Nourish("images/spells/nourish-icon.png", "Nourish", "learn " + SpellType.Nourish.ordinal()),
    HolyShield("images/spells/holyshield-icon.png", "Holy Shield", "learn " + SpellType.HolyShield.ordinal()),

    PoisonArrow("images/spells/poisonarrow-icon.png", "Poison Arrow", "learn " + SpellType.PoisonArrow.ordinal()),
    Decrepify("images/spells/decrepify-icon.png", "Decrepify", "learn " + SpellType.Decrepify.ordinal()),
    Weaken("images/spells/weaken-icon.png", "Weaken", "learn " + SpellType.Weaken.ordinal()),

    MultiBouncer("images/spells/multibouncer-icon.png", "Multi Bouncer", "learn " + SpellType.MultiBouncer.ordinal()),
    Bouncer("images/spells/bouncer-icon.png", "Bouncer", "learn " + SpellType.Bouncer.ordinal()),
    Empty3("images/spells/null-icon.png", "Empty", "learn " + SpellType.Null.ordinal()),

    Empty4("images/spells/null-icon.png", "Empty", "learn " + SpellType.Null.ordinal()),
    Empty5("images/spells/null-icon.png", "Empty", "learn " + SpellType.Null.ordinal()),
    Empty6("images/spells/null-icon.png", "Empty", "learn " + SpellType.Null.ordinal()),

    Null("images/null.png", "null", "null");

    private String iconPath, name, command;

    private PassiveType(String iconPath, String name, String command) {
        this.iconPath = iconPath;
        this.name = name;
        this.command = command;
    }

    public String getIconPath() {
        return iconPath;
    }

    public String getName() {
        return name;
    }

    public String getCommand() {
        return command;
    }
}
