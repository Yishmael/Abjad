package data;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Player extends Creature {

    public Inventory inventory;
    public Menu menu;
    private Item[] items;
    public boolean showInventory, showMenu;
    public int tileIndex;
    public int[] direction;
    private int itemsCount;
    public int[] matrix = { 0, 0, 202, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, };

    public Player(Image image, String name, float xPos, float yPos, int health, float speed, MapTile mapTile)
            throws SlickException {
        super(image, name, xPos, yPos, health, speed, mapTile);
        showInventory = false;
        showMenu = false;
        tileIndex = 0;
        items = new Item[30];
        direction = new int[2];
        // pass strings to inventory instead of <item>;
        inventory = new Inventory(new Image("images/ui/inventory1.png"), matrix);
        menu = new Menu(new Image("images/ui/menu.png"));
        itemsCount = 1;
    }

    public void addItem(Item item) {
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i] == 0) {
                matrix[i] = getItemID(item);
                itemsCount++;
                break;
            }
        }
    }

    private int getItemID(Item item) {
        int result = 0;
        switch (item.getName()) {
        case "Axe1":
            result = 101;
            break;
        case "Axe2":
            result = 102;
            break;
        case "Shield1":
            result = 201;
            break;
        case "Shield2":
            result = 202;
            break;
        case "Boulder1":
            result = 501;
            break;
        default:
            break;
        }

        return result;

    }

    public Item[] getItems() {
        return items;
    }

    public boolean inventoryFull() {
        return itemsCount >= 30;
        // return items.size() >= 30;
    }

    @Override
    public void move(float x, float y) {
        // TODO Auto-generated method stub

    }

    public void removeItem(int slot) {
        // items.get(slot).visible = false;
        // items.remove(slot);
        matrix[slot] = 0;
        itemsCount--;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(image, xPos, yPos);
    }

    @Override
    public void update(int dt) {
        delta = (float) dt / 1000;

        int xDir = direction[0];
        int yDir = direction[1];

        int x = (int) (xPos - (image.getWidth() / 2) * xDir + image.getWidth() / 2);
        int y = (int) (yPos - (image.getHeight() / 2) * yDir + image.getHeight() / 2);

        int xTile = mapTile.getTileX(x);
        int yTile = mapTile.getTileY(y);

        // System.out.println(x + ":" + y + " " + xTile + ":" + yTile);

        // System.out.println(xTile + " " + yTile);
        // System.out.println(mapTile.getTileType(xTile, yTile));

        // System.out.println((xTile) + " " + (yTile));
        if (direction[0] != 0 && direction[1] != 0) {
            if (mapTile.getTileType(xTile + xDir, yTile).isWalkable())
                if (mapTile.getTileType(xTile, yTile + yDir).isWalkable())
                    if (mapTile.getTileType(xTile + xDir, yTile + yDir).isWalkable()) {
                        xPos += xDir * (speed * delta);
                        yPos += yDir * (speed * delta);
                    }
        } else if (mapTile.getTileType(xTile + xDir, yTile + yDir).isWalkable()) {
            xPos += xDir * (speed * delta);
            yPos += yDir * (speed * delta);
        }
    }

}
