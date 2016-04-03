package components.units;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import components.Component;
import components.ItemComponent;
import loaders.FontLoader;
import others.Button;
import others.Consts;
import others.Entity;
import others.MessageChannel;

public class InventoryComponent implements Component {
    private long id = Consts.INVENTORY;
    private Entity self;

    private Image inventoryImage;
    private Graphics g = new Graphics();
    private boolean shown = false;
    private ArrayList<Entity> unequippedItems = new ArrayList<Entity>();
    private FontLoader font = new FontLoader("verdana", 9, java.awt.Color.YELLOW);
    // TODO move this to Consts
    int ratio = 1;

    private int capacity = 20;
    private Image gearPane;
    private ArrayList<Entity> equippedItems = new ArrayList<Entity>();
    private Button[] gearFrames = new Button[8];
    private Button[] inventoryFrames = new Button[capacity];
    private Button[] frames = new Button[gearFrames.length + inventoryFrames.length];

    public InventoryComponent(Entity self, ArrayList<Entity> items) throws SlickException {
        this.self = self;
        if (items != null) {
            for (Entity item: items) {
                if (item != null) {
                    if (unequippedItems.size() < capacity) {
                        unequippedItems.add(item);
                    }
                }
            }
        }
        inventoryImage = new Image("res/images/ui/inventory1.png");
        gearPane = new Image("res/images/ui/iface2.png");

        inventoryFrames = new Button[20];
        for (int i = 0; i < 20; i++) {
            float x = 5 + 45 * (ratio * (i - 5 * (i / 5)));
            float y = 5 + Consts.SCREEN_HEIGHT * 0.65f + ratio * 45 * (i / 5);
            inventoryFrames[i] = new Button(null, new Rectangle(x, y, ratio * 40, ratio * 40), "slot " + i,
                    "slot" + i + " cmd");
        }

        gearFrames[0] = new Button(null, new Rectangle(ratio * 90, ratio * 30, ratio * 40, ratio * 40), "Helm",
                "helm command");
        gearFrames[1] = new Button(null, new Rectangle(ratio * 20, ratio * 80, ratio * 50, ratio * 80), "Weapon",
                "weap command");
        gearFrames[2] = new Button(null, new Rectangle(ratio * 80, ratio * 80, ratio * 60, ratio * 100), "Chest",
                "chest command");
        gearFrames[3] = new Button(null, new Rectangle(ratio * 150, ratio * 80, ratio * 50, ratio * 80), "Shield",
                "shield command");
        gearFrames[4] = new Button(null, new Rectangle(ratio * 150, ratio * 175, ratio * 50, ratio * 50), "Boots",
                "boots command");
        gearFrames[5] = new Button(null, new Rectangle(ratio * 20, ratio * 175, ratio * 50, ratio * 50), "Gloves",
                "gloves command");
        gearFrames[6] = new Button(null, new Rectangle(ratio * 150, ratio * 40, ratio * 30, ratio * 30), "Necklace",
                "necklace command");
        gearFrames[7] = new Button(null, new Rectangle(ratio * 80, ratio * 190, ratio * 60, ratio * 20), "Belt",
                "belt command");

        for (int i = 0; i < gearFrames.length; i++) {
            frames[i] = gearFrames[i];
        }

        for (int i = 0; i < inventoryFrames.length; i++) {
            frames[i + gearFrames.length] = inventoryFrames[i];
        }
    }

    public void addItem(Entity item) {
        if (item != null) {
            if (unequippedItems.size() < capacity) {
                unequippedItems.add(item);
                System.out.println("Picked up: " + item.getName());
            }
        }
    }

    public void drawFrame(Button frame) {
        for (Button f: frames) {
            if (f == frame) {
                f.drawDescription(g, font);
            }
        }
    }

    public void draw() {
        if (shown) {
            g.drawImage(gearPane, 0, 0, Consts.SCREEN_WIDTH * 0.34f, Consts.SCREEN_HEIGHT * 0.65f, 0, 0,
                    gearPane.getWidth(), gearPane.getHeight());
            g.drawImage(inventoryImage, 0, Consts.SCREEN_HEIGHT * 0.65f, Consts.SCREEN_WIDTH * 0.34f,
                    Consts.SCREEN_HEIGHT, 0, 0, inventoryImage.getWidth(), inventoryImage.getHeight());

            for (int i = 0; i < unequippedItems.size(); i++) {
                if (inventoryFrames[i]
                        .getImage() != ((SpriteComponent) unequippedItems.get(i).getComponent(Consts.SPRITE))
                                .getImage()) {
                    inventoryFrames[i].setImage(
                            ((SpriteComponent) unequippedItems.get(i).getComponent(Consts.SPRITE)).getImage());
                    inventoryFrames[i].setDescription(unequippedItems.get(i).getName());
                }
                inventoryFrames[i].draw(g);
            }

            for (int i = 0; i < gearFrames.length; i++) {
                gearFrames[i].draw(g);
            }
            for (int i = 0; i < equippedItems.size(); i++) {
                int index = 0;
                String name = equippedItems.get(i).getName();
                if (name.contains("Helm")) {
                    index = 0;
                } else if (name.contains("Weapon")) {
                    index = 1;
                } else if (name.contains("Chest")) {
                    index = 2;
                } else if (name.contains("Shield")) {
                    index = 3;
                } else if (name.contains("Boots")) {
                    index = 4;
                } else if (name.contains("Gloves")) {
                    index = 5;
                } else if (name.contains("Necklace")) {
                    index = 6;
                } else if (name.contains("Belt")) {
                    index = 7;
                }
                if (gearFrames[index].getImage() != ((SpriteComponent) equippedItems.get(i).getComponent(Consts.SPRITE))
                        .getImage()) {
                    gearFrames[index]
                            .setImage(((SpriteComponent) equippedItems.get(i).getComponent(Consts.SPRITE)).getImage());
                    ArrayList<String> bonuses = new ArrayList<String>();
                    for (Component comp: equippedItems.get(i).getComponents()) {
                        if (ItemComponent.class.isInstance(comp)) {
                            for (String bonus: ((ItemComponent) comp).giveBonuses()) {
                                bonuses.add(bonus);
                            }
                        }
                    }
                    gearFrames[index].setDescriptionList(bonuses);
                }

                gearFrames[index].draw(g);
                int row = 0;
                for (String bonus: gearFrames[index].getDescriptionList()) {
                    font.draw(gearFrames[index].getX(), gearFrames[index].getY() + 13 * row, bonus);
                    row++;
                }
            }
        }
    }

    public void nextItem() {
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
    }

    public void receive(String command) {
        String str = command;
        if (str.matches("toggleInv")) {
            shown = !shown;
        } else if (str.matches("invOff")) {
            shown = false;
        } else if (str.matches("invOn")) {
            shown = true;
        } else if (str.matches("next item")) {
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
        }
    }

    public void equip(Entity item) {
        for (Component comp: item.getComponents()) {
            if (ItemComponent.class.isInstance(comp)) {
                ItemComponent itemComp = (ItemComponent) comp;
                for (String bonus: itemComp.giveBonuses()) {
                    self.broadcast(bonus);
                }
            }
        }
        unequippedItems.remove(item);
        if (!item.hasComponent(Consts.CONSUMABLE)) {
            equippedItems.add(item);
            // System.out.println(item.getName());
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
                for (String bonus: itemComp.negateBonuses()) {
                    self.broadcast(bonus);
                }
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

    public Button[] getGearFrames() {
        return gearFrames;
    }

    public Button[] getInventoryFrames() {
        return inventoryFrames;
    }

    public Button[] getFrames() {
        return frames;
    }

    public void toggleInv() {
        shown = !shown;
    }

    public boolean isShown() {
        return shown;
    }

    public long getID() {
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

    public void showInventory(boolean show) {
        shown = show;
    }

    public void update() {

    }
}
