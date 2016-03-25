package components.items;

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
    public String[] giveBonuses() {
        String[] temp = new String[3];
        int i = 0;
        if (strengthBonus != 0) {
            temp[i++] = "STR " + strengthBonus;
        }
        if (agilityBonus != 0) {
            temp[i++] = "AGI " + agilityBonus;
        }
        if (intelligenceBonus != 0) {
            temp[i++] = "INT " + intelligenceBonus;
        }
        // returning only those strings whose bonuses are not zero
        String bonuses[] = new String[i];
        for (int j = 0; j < i; j++) {
            bonuses[j] = temp[j];
        }

        return bonuses;
    }

    @Override
    public String[] negateBonuses() {
        return new String[] { "STR " + -strengthBonus, "AGI " + -agilityBonus, "INT " + -intelligenceBonus };
    }

    @Override
    public long getID() {
        return id;
    }

}
