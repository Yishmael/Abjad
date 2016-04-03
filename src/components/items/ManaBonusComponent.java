package components.items;

import java.util.ArrayList;

import components.Component;
import components.ItemComponent;
import others.Consts;
import others.Entity;
import others.MessageChannel;

public class ManaBonusComponent implements Component, ItemComponent {
    private long id = Consts.MANABONUS;
    private Entity self;
    private float manaBonus;

    public ManaBonusComponent(Entity self, float manaBonus) {
        this.self = self;
        this.manaBonus = manaBonus;
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
        list.add("MPcap " + manaBonus);

        return list;
    }

    @Override
    public ArrayList<String> negateBonuses() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("MPcap " + -manaBonus);

        return list;
    }

    public float getManaBonus() {
        return manaBonus;
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
