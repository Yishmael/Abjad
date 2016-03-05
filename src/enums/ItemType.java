package enums;

public enum ItemType {

    Axe("Shiny Axe", "images/items/axe1.png", 30, 0, 0.8f),
    Sword("Steel Sword", "images/items/sword1.png", 80, 0, 1.7f),
    Branch("Olive Branch", "images/items/branch1.png", 2, 0, 0.2f),
    Shield("Red Shield", "images/items/shield1.png", 0, 50, 0),
    Null("Null", "images/null.png", 0, 0, 0);

    private String name, imagePath;
    private float damage, defense, cooldown;

    ItemType(String name, String imagePath, float damage, float defense,
            float cooldown) {
        this.name = name;
        this.imagePath = imagePath;
        this.damage = damage;
        this.defense = defense;
        this.cooldown = cooldown;
    };

    public String toString() {
        String str = name + " (" + (int) damage + " dmg, " + (int) defense
                + " def, " + cooldown + "sec cd)";
        return str;
    }

    public String getName() {
        return name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public float getDamage() {
        return damage;
    }

    public float getDefense() {
        return defense;
    }

    public float getCooldown() {
        return cooldown;
    }
}
