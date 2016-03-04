package enums;

public enum PassiveType {

    Strength("images/passives/strength.png"),
    Agility("images/passives/agility.png"),
    Intelligence("images/passives/intelligence.png"),
    Null("images/null.png");

    private String iconPath;

    private PassiveType(String iconPath) {
        this.iconPath = iconPath;
    }
    
    public String getIconPath() {
        return iconPath;
    }
}
