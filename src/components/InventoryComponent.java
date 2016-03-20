package components;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import components.items.ConsumableComponent;
import components.items.ItemComponent;
import fonts.Text;
import others.Consts;
import others.Entity;
import others.MessageChannel;

public class InventoryComponent implements Component {
    private int id = Consts.INVENTORY;
    private Entity self;

    private float width = Consts.SCREEN_WIDTH * 0.66f, height = Consts.SCREEN_HEIGHT * 0.33f;
    private Image inventoryImage;
    private Graphics g = new Graphics();
    private boolean shown = false;
    private ArrayList<Entity> unequippedItems = new ArrayList<Entity>();
    private Text font = new Text("fonts/verdana.ttf", java.awt.Color.YELLOW);

    private int capacity = 16;
    private Image gearPane;
    private ArrayList<Entity> equippedItems = new ArrayList<Entity>();
    private Rectangle[] frames = { new Rectangle(90, 30, 40, 40), new Rectangle(20, 80, 50, 80),
            new Rectangle(80, 80, 60, 100), new Rectangle(150, 80, 50, 80), new Rectangle(150, 175, 50, 50),
            new Rectangle(20, 175, 50, 50), new Rectangle(150, 40, 30, 30), new Rectangle(80, 190, 60, 20) };

    public InventoryComponent(Entity self, ArrayList<Entity> items) throws SlickException {
        this.inventoryImage = new Image("images/ui/inventory1.png");
        this.self = self;
        if (items != null) {
            for (Entity item: items) {
                if (item != null) {
                    if (this.unequippedItems.size() < capacity) {
                        this.unequippedItems.add(item);
                    }
                }
            }
        }
        this.gearPane = new Image("images/ui/iface2.png");
    }

    public void addItem(Entity item) {
        if (item != null) {
            if (unequippedItems.size() < capacity) {
                unequippedItems.add(item);
            }
        }
    }

    public void draw() {
        if (shown) {
            g.drawImage(inventoryImage, 0, Consts.SCREEN_HEIGHT * 0.56f, Consts.SCREEN_WIDTH * 0.34f,
                    Consts.SCREEN_HEIGHT, 0, 0, width, height);
            for (int i = 0, j = 0; i < unequippedItems.size(); i++) {
                if (i == 5 || i == 10) {
                    j++;
                }
                int x = 40 * (i - 5 * j);
                int y = Consts.SCREEN_HEIGHT - 192 + 40 * j;
                ((SpriteComponent) unequippedItems.get(i).getComponent(Consts.SPRITE)).draw(x, y, 0, 2f);
                ((SpriteComponent) unequippedItems.get(i).getComponent(Consts.SPRITE)).draw();
                g.draw(new Rectangle(x, y, 40, 40));
            }

            g.drawImage(gearPane, 0, 0, Consts.SCREEN_WIDTH * 0.34f, Consts.SCREEN_HEIGHT * 0.60f, 0, 0,
                    gearPane.getWidth(), gearPane.getHeight());

            for (int i = 0; i < frames.length; i++) {
                g.draw(frames[i]);
            }
            for (int i = 0; i < equippedItems.size(); i++) {
                int index = 0;
                Image img = ((SpriteComponent) equippedItems.get(i).getComponent(Consts.SPRITE)).getImage();
                switch (equippedItems.get(i).getName()) {
                case "Helmet":
                    index = 0;
                    break;
                case "Weapon":
                    index = 1;
                    break;
                case "Chest":
                    index = 2;
                    break;
                case "Shield":
                    index = 3;
                    break;
                case "Boots":
                    index = 4;
                    break;
                case "Gloves":
                    index = 5;
                    break;
                case "Necklace":
                    index = 6;
                    break;
                case "Belt":
                    index = 7;
                    break;
                default:
                    break;
                }

                g.drawImage(img, frames[index].getX(), frames[index].getY(),
                        frames[index].getX() + frames[index].getWidth(),
                        frames[index].getY() + frames[index].getHeight(), 0, 0, img.getWidth(), img.getHeight());
                int row = 0;
                for (Component comp: equippedItems.get(i).getComponents())
                    if (ItemComponent.class.isInstance(comp)) {
                        font.draw(frames[index].getX(), frames[index].getY() + row * 16,
                                String.valueOf(((ItemComponent) comp).giveBonus()));
                        row++;
                    }
            }
        }
    }

    public void equip(Entity item) {
        for (Component comp: item.getComponents()) {
            if (ItemComponent.class.isInstance(comp)) {
                ItemComponent itemComp = (ItemComponent) comp;
                self.broadcast(itemComp.giveBonus());
            }
        }
        unequippedItems.remove(item);
        if (!item.hasComponent(Consts.CONSUMABLE)) {
            equippedItems.add(item);
        }
        // if (currentItem.hasComponent(Consts.ATTACK)) {
        // AttackComponent attack = (AttackComponent)
        // currentItem.getComponent(Consts.ATTACK);
        // self.broadcast(attack.giveBonus());
        // }
        // if (currentItem.hasComponent(Consts.HEALTHBONUS)) {
        // HealthBonusComponent healthBonus = (HealthBonusComponent)
        // currentItem.getComponent(Consts.HEALTHBONUS);
        // self.broadcast(healthBonus.giveBonus());
        // }
        // if (currentItem.hasComponent(Consts.DEFENSE)) {
        // DefenseComponent defense = (DefenseComponent)
        // currentItem.getComponent(Consts.DEFENSE);
        // self.broadcast(defense.giveBonus());
        // }
        // if (currentItem.hasComponent(Consts.MANABONUS)) {
        // ManaBonusComponent manaBonus = (ManaBonusComponent)
        // currentItem.getComponent(Consts.MANABONUS);
        // self.broadcast(manaBonus.giveBonus());
        // }
    }

    public void unequip(Entity item) {
        for (Component comp: item.getComponents()) {
            if (ItemComponent.class.isInstance(comp)) {
                ItemComponent itemComp = (ItemComponent) comp;
                self.broadcast(itemComp.negateBonus());
            }
        }
        equippedItems.remove(item);
        unequippedItems.add(item);
        // if (currentItem.hasComponent(Consts.HEALTHBONUS)) {
        // HealthBonusComponent healthBonus = (HealthBonusComponent)
        // currentItem.getComponent(Consts.HEALTHBONUS);
        // self.broadcast(healthBonus.negateBonus());
        // }
        // if (currentItem.hasComponent(Consts.MANABONUS)) {
        // ManaBonusComponent manaBonus = (ManaBonusComponent)
        // currentItem.getComponent(Consts.MANABONUS);
        // self.broadcast(manaBonus.negateBonus());
        // }
    }

    public int getID() {
        return id;
    }

    public int getItemCount() {
        return unequippedItems.size();
    }

    public boolean hasItem(Entity item) {
        if (item == null) {
            return false;
        }
        for (Entity i: unequippedItems) {
            if (i == item) {
                return true;
            }
        }
        return false;
    }

    public boolean isEquipped(String itemName) {
        for (Entity equipped: equippedItems) {
            if (equipped.getName() == itemName)
                return true;
        }
        return false;
    }

    public void process(MessageChannel channel) {

    }

    public void receive(String command) {
        String str = command;
        if (str.matches("toggleInv")) {
            shown = !shown;
            return;
        }
        if (str.matches("invOff")) {
            shown = false;
            return;
        }
        if (str.matches("invOn")) {
            shown = true;
            return;
        }
        if (str.matches("next item")) {
            if (unequippedItems.size() > 0) {
                for (Entity equipped: equippedItems) {
                    if (equipped.getName().matches(unequippedItems.get(0).getName())) {
                        unequip(equipped);
                        equip(unequippedItems.get(0));
                        return;
                    }
                }
                equip(unequippedItems.get(0));
            }
            return;
        }
    }

    public void showInventory(boolean show) {
        shown = show;
    }

    public void update() {

    }
}
