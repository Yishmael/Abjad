package components.items;

import java.util.ArrayList;

import components.Component;
import components.ItemComponent;
import others.Consts;
import others.Entity;
import others.MessageChannel;

public class MovementSpeedBonusComponent implements Component, ItemComponent {
    private long id = Consts.MOVEMENTBONUS;
    private Entity self;
    private float speedBonus;

    public MovementSpeedBonusComponent(Entity self, float moveBonus) {
        this.self = self;
        this.speedBonus = moveBonus;
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
        list.add("MS " + speedBonus + "%");

        return list;
    }

    @Override
    public ArrayList<String> negateBonuses() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("MS " + -speedBonus + "%");

        return list;
    }

    public float getMovementBonus() {
        return speedBonus;
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
