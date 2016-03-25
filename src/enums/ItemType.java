package enums;

public enum ItemType {

    Axe("Shiny axe", "res/images/items/axe1.png"),
    Sword("Steel sword", "res/images/items/sword1.png"),
    Wand("Magic wand", "res/images/items/wand1.png"),
    Shield("Iron shield", "res/images/items/shield1.png"),
    Spear("Black spear", "res/images/items/spear1.png"),
    Helm("Metal helm", "res/images/items/helm1.png"),
    Chest("Chestguards", "res/images/items/chest1.png"),
    Boots("Light boots", "res/images/items/boots1.png"),
    Necklace("Necklace", "res/images/items/necklace1.png"),
    Gloves("Light gloves", "res/images/items/gloves1.png"),
    Belt("Light belt", "res/images/items/belt1.png"),
    HealingPotion1("Healing potion", "res/images/items/healingpotion1.png"),
    HealingPotion2("Healing potion", "res/images/items/healingpotion2.png"),
    ReplenishingPotion1("Replenishing potion", "res/images/items/replenishingpotion1.png"),
    ReplenishingPotion2("Replenishing potion", "res/images/items/replenishingpotion2.png"),
    SwiftnessPotion1("Swiftness Potion", "res/images/items/swiftnesspotion1.png"),
    SwiftnessPotion2("Swiftness Potion", "res/images/items/swiftnesspotion2.png"),
    Cherry("Cherry", "res/images/items/cherry1.png"),
    Null("Null", "res/images/null.png");

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
