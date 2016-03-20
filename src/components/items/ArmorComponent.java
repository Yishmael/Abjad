package components.items;

import components.Component;
import others.Consts;
import others.Entity;
import others.MessageChannel;

public class ArmorComponent implements Component, ItemComponent {
    private int id = Consts.ARMOR;
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
    public String giveBonus() {
        return "DEF " + defense;
    }

    @Override
    public String negateBonus() {
        return "DEF " + -defense;
    }

    public float getDefense() {
        return defense;
    }

    @Override
    public int getID() {
        return id;
    }
}
