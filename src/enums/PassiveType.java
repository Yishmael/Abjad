package enums;

public enum PassiveType {

    Strength("res/images/passives/stats/strength.png", "Strength", "STR++"),
    Agility("res/images/passives/stats/agility.png", "Agility", "AGI++"),
    Intelligence("res/images/passives/stats/intelligence.png", "Intelligence", "INT++"),

    Cleave("res/images/passives/cleave.png", "+7 Cleave radius", "CLV 7%"),
    LifeSteal("res/images/passives/lifesteal.png", "+5% Life steal", "LS 5%"),
    Damage("res/images/passives/damage.png", "+5% Damage", "DMG 5%"),

    Critical("res/images/passives/critical.png", "+5% Critical", "CRT 5%"),
    MoveSpeed("res/images/passives/movespeed.png", "+2% Movement speed", "MS 2%"),
    AttackSpeed("res/images/passives/attackspeed.png", "+5% Attack speed", "AS 5%"),

    SpellPower("res/images/passives/spellpower.png", "+5% Spell power", "SP 5%"),
    ManaCap("res/images/passives/manacap.png", "+50 Mana capacity", "MPcap 50"),
    ManaRegen("res/images/passives/manaregen.png", "+3 Mana regeneration", "MPregen 3"),

    CoFireball("res/images/spells/cofireball-icon.png", "CoFireball", "learn " + SpellType.CoFireball.ordinal()),
    Fireball("res/images/spells/fireball-icon.png", "Fireball", "learn " + SpellType.Fireball.ordinal()),
    ScorchedEarth(
            "res/images/spells/scorchedearth-icon.png",
            "Scorched Earth",
            "learn " + SpellType.ScorchedEarth.ordinal()),

    ReviveMinion("res/images/spells/reviveminion-icon.png", "Revive Minion", "learn " + SpellType.ReviveMinion.ordinal()),
    SummonKirith("res/images/spells/summonkirith-icon.png", "Summon Kirith", "learn " + SpellType.SummonKirith.ordinal()),
    SummonWall("res/images/spells/summonwall-icon.png", "Summon Wall", "learn " + SpellType.SummonWall.ordinal()),

    Teleport("res/images/spells/teleport-icon.png", "Teleport", "learn " + SpellType.Teleport.ordinal()),
    Heal("res/images/spells/heal-icon.png", "Heal", "learn " + SpellType.Heal.ordinal()),
    HolyShield("res/images/spells/holyshield-icon.png", "Holy Shield", "learn " + SpellType.HolyShield.ordinal()),

    PoisonArrow("res/images/spells/poisonarrow-icon.png", "Poison Arrow", "learn " + SpellType.PoisonArrow.ordinal()),
    Decrepify("res/images/spells/decrepify-icon.png", "Decrepify", "learn " + SpellType.Decrepify.ordinal()),
    Weaken("res/images/spells/weaken-icon.png", "Weaken", "learn " + SpellType.Weaken.ordinal()),

    MultiBouncer("res/images/spells/multibouncer-icon.png", "Multi Bouncer", "learn " + SpellType.MultiBouncer.ordinal()),
    Bouncer("res/images/spells/bouncer-icon.png", "Bouncer", "learn " + SpellType.Bouncer.ordinal()),
    Empty3("res/images/spells/null-icon.png", "Empty", "learn " + SpellType.Null.ordinal()),

    Empty4("res/images/spells/null-icon.png", "Empty", "learn " + SpellType.Null.ordinal()),
    Empty5("res/images/spells/null-icon.png", "Empty", "learn " + SpellType.Null.ordinal()),
    Empty6("res/images/spells/null-icon.png", "Empty", "learn " + SpellType.Null.ordinal()),

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
