package enums;

public enum PassiveType {

    Strength("images/passives/stats/strength.png", "Strength", "STR++"),
    Agility("images/passives/stats/agility.png", "Agility", "AGI++"),
    Intelligence("images/passives/stats/intelligence.png", "Intelligence", "INT++"),

    Cleave("images/passives/cleave.png", "+7 Cleave radius", "CLV 7"), //TODO make a cone instead of a circle
    LifeSteal("images/passives/lifesteal.png", "+5% Life steal", "LS 5"),
    Damage("images/passives/damage.png", "+5% Damage", "DMG 5"),

    Critical("images/passives/critical.png", "+5% Critical", "CRT 5"),
    MoveSpeed("images/passives/movespeed.png", "+2% Movement speed", "MS 2"),
    AttackSpeed("images/passives/attackspeed.png", "+5% Attack speed", "AS 5"),

    SpellPower("images/passives/spellpower.png", "+5% Spell power", "SP 5"),
    ManaCap("images/passives/manacap.png", "+50 Mana capacity", "MPcap 50"),
    ManaRegen("images/passives/manaregen.png", "+3 Mana regeneration", "MPregen 3"),

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
