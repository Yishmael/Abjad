package enums;

public enum ItemType {

    Axe("Shiny axe", "images/items/axe1.png"),
    Sword("Steel sword", "images/items/sword1.png"),
    Branch("Olive branch", "images/items/branch1.png"),
    Shield("Red shield", "images/items/shield1.png"),
    Spear("Black spear", "images/items/spear1.png"),
    Helmet("Protector's helmet", "images/items/helmet1.png"),
    Chest("Breastplate of might", "images/items/chest1.png"),
    Boots("Running boots", "images/items/boots1.png"),
    Necklace("Magical necklace", "images/items/necklace1.png"),
    Gloves("Light gloves", "images/items/gloves1.png"),
    Belt("Light belt", "images/items/belt1.png"),
    HealingPotion("Healing potion", "images/items/healingpotion1.png"),
    ReplenishingPotion("Replenishing potion", "images/items/replenishingpotion1.png"),
    Cherry("Cherry", "images/items/cherry1.png"),
    Null("Null", "images/null.png");

    private String name, imagePath;

    ItemType(String name, String imagePath) {
        this.name = name;
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getName() {
        return name;
    }
}
