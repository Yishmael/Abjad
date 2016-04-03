package components.items;

import java.util.ArrayList;

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
    public ArrayList<String> giveBonuses() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("DEF " + defense);
        return list;
    }

    @Override
    public ArrayList<String> negateBonuses() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("DEF " + -defense);

        return list;
    }

    public float getDefense() {
        return defense;
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
