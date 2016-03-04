package items;

public abstract class Item {
    private String name, iconPath;

    public Item(String name, String iconPath) {
        this.name = name;
        this.iconPath = iconPath;
    }

    public String getName() {
        return name;
    }
    
    public String getIconPath() {
        return iconPath;
    }
}
