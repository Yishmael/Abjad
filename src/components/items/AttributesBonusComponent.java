package components.items;

import java.util.ArrayList;

import components.Component;
import components.ItemComponent;
import others.Consts;
import others.Entity;
import others.MessageChannel;

public class AttributesBonusComponent implements Component, ItemComponent {
    private long id = Consts.ATTRIBUTESBONUS;
    private Entity self;
    private int strengthBonus, agilityBonus, intelligenceBonus;

    public AttributesBonusComponent(Entity self, int strengthBonus, int agilityBonus, int intelligenceBonus) {
        this.self = self;
        this.strengthBonus = strengthBonus;
        this.agilityBonus = agilityBonus;
        this.intelligenceBonus = intelligenceBonus;
    }

    @Override
    public void process(MessageChannel channel) {

    }

    @Override
    public void receive(String command) {

    }

    @Override
    public void update() {

    }

    @Override
    public ArrayList<String> giveBonuses() {
        ArrayList<String> list = new ArrayList<String>();
        if (strengthBonus != 0) {
            list.add("STR " + strengthBonus);
        }
        if (agilityBonus != 0) {
            list.add("AGI " + agilityBonus);
        }
        if (intelligenceBonus != 0) {
            list.add("INT " + intelligenceBonus);
        }

        return list;
    }

    @Override
    public ArrayList<String> negateBonuses() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("STR " + -strengthBonus);
        list.add("AGI " + -agilityBonus);
        list.add("INT " + -intelligenceBonus);

        return list;
    }

    @Override
    public long getID() {
        return id;
    }

    @Override
    public void draw() {
        // TODO Auto-generated method stub

    }

}
