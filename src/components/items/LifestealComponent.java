package components.items;

import java.util.ArrayList;

import components.Component;
import components.ItemComponent;
import others.Consts;
import others.Entity;
import others.MessageChannel;

public class LifestealComponent implements Component, ItemComponent {
    private long id = Consts.LIFESTEAL;
    private Entity self;

    private float lifesteal;

    public LifestealComponent(Entity self, float lifesteal) {
        this.self = self;
        this.lifesteal = lifesteal;
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub

    }

    @Override
    public void receive(String command) {
    }

    @Override
    public void process(MessageChannel channel) {
        // TODO Auto-generated method stub

    }

    @Override
    public ArrayList<String> giveBonuses() {
        ArrayList<String> list = new ArrayList<String>();

        return list;
    }

    @Override
    public ArrayList<String> negateBonuses() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("LS " + -lifesteal + "%");

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
