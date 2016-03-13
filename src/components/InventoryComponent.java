package components;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;

import enums.ItemType;
import others.Consts;
import others.Entity;
import others.MessageChannel;

public class InventoryComponent implements Component {
    private int id = Consts.INVENTORY;
    private Image image;
    private Image[] itemImages;
    private float width, height;
    private Graphics g;
    private boolean shown = false;
    private ItemType[] items;
    private ItemType currentItem;
    private int currentIndex;
    private Entity self;

    public InventoryComponent(Entity self, String imagePath, ItemType[] startingItems) throws SlickException {
        this.self = self;
        this.image = new Image(imagePath);
        this.width = image.getWidth();
        this.height = image.getHeight();
        items = new ItemType[30];
        for (int i = 0; i < startingItems.length; i++) {
            items[i] = startingItems[i];
        }
        for (int i = startingItems.length; i < 30; i++) {
            items[i] = ItemType.Null;
        }
        g = new Graphics();
        itemImages = new Image[ItemType.values().length];
        for (int i = 0; i < itemImages.length; i++) {
            itemImages[i] = new Image(ItemType.values()[i].getImagePath());
        }
    }

    public InventoryComponent(Entity self, String imagePath) throws SlickException {
        this.self = self;
        this.image = new Image(imagePath);
        this.width = image.getWidth();
        this.height = image.getHeight();
        items = new ItemType[30];
        for (int i = 0; i < 30; i++) {
            items[i] = ItemType.Null;
        }
        g = new Graphics();
        itemImages = new Image[ItemType.values().length];
        for (int i = 0; i < itemImages.length; i++) {
            itemImages[i] = new Image(ItemType.values()[i].getImagePath());
        }
    }

    public void addItem(ItemType item) {
        if (getItemsCount() < 30) {
            for (int i = 0; i < items.length; i++) {
                if (items[i] == ItemType.Null) {
                    items[i] = item;
                    break;
                }
            }
        }
    }

    public boolean hasItem(ItemType item) {
        for (ItemType i: items) {
            if (item == i) {
                return true;
            }
        }
        return false;
    }

    public int getItemsCount() {
        int count = 0;
        for (int i = 0; i < items.length; i++) {
            if (items[i] != ItemType.Null) {
                count++;
            }
        }
        return count;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public void process(MessageChannel channel) {

    }

    @Override
    public void receive(String command) {
        String str = command;
        if (str.matches("toggleInv")) {
            shown = !shown;
            return;
        }
        if (str.matches("invOff")) {
            // shown = false;
            return;
        }
        if (str.matches("next item")) {
            currentIndex++;
            currentIndex %= 30;
            while (items[currentIndex] == ItemType.Null) { // first while!
                currentIndex++;
                currentIndex %= 30;
            }
            currentItem = items[currentIndex];
            self.broadcast("equipped " + currentItem.ordinal());
            return;
        }
    }

    public void showInventory(boolean show) {
        shown = show;
    }

    @Override
    public void update() {

    }

    public void draw() {
        if (shown) {
            g.drawImage(image, 0, Consts.SCREEN_HEIGHT * 0.7f, Consts.SCREEN_WIDTH * 0.6f, Consts.SCREEN_HEIGHT, 0, 0,
                    width, height, new Color(255, 150, 150, 150));
            for (int i = 0, j = 0; i < 30; i++) {
                if (i == 10 || i == 20) {
                    j++;
                }
                if (items[i] != ItemType.Null) {
                    g.drawImage(itemImages[items[i].ordinal()], 64 * (i - 10 * j), 320 + 64 * j);
                    if (i == currentIndex) {
                        g.draw(new Circle(64 * (i - 10 * j) + 25, 320 + 64 * j + 25, 25));
                    }
                }
            }
        }
    }
}
