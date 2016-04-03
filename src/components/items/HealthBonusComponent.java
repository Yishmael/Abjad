package components.items;

import java.util.ArrayList;

import components.Component;
import components.ItemComponent;
import others.Consts;
import others.Entity;
import others.MessageChannel;

public class HealthBonusComponent implements Component, ItemComponent {
    private long id = Consts.HEALTHBONUS;
    private Entity self;
    private float healthBonus;

    public HealthBonusComponent(Entity self, float healthBonus) {
        this.self = self;
        this.healthBonus = healthBonus;
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
        list.add("HPcap " + healthBonus);

        return list;
    }

    @Override
    public ArrayList<String> negateBonuses() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("HPcap " + -healthBonus);

        return list;
    }

    public float getHealthBonus() {
        return healthBonus;
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
