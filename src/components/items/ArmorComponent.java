package components.items;

import components.Component;
import components.ItemComponent;
import others.Consts;
import others.Entity;
import others.MessageChannel;

public class ArmorComponent implements Component, ItemComponent {
    private long id = Consts.ARMOR;
    private Entity self;

    private float defense;

    public ArmorComponent(Entity self, float defense) {
        this.self = self;
        this.defense = defense;
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
        return new String[] { "DEF " + defense };
    }

    @Override
    public String[] negateBonuses() {
        return new String[] { "DEF " + -defense };
    }

    public float getDefense() {
        return defense;
    }

    @Override
    public long getID() {
        return id;
    }
}
