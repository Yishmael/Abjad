package components.items;

import components.Component;
import others.Consts;
import others.Entity;
import others.MessageChannel;

public class ManaBonusComponent implements Component, ItemComponent {
    private int id = Consts.MANABONUS;
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
    public String giveBonus() {
        return "MPcap " + manaBonus;
    }

    @Override
    public String negateBonus() {
        return "MPcap " + -manaBonus;
    }

    public float getManaBonus() {
        return manaBonus;
    }

    @Override
    public int getID() {
        return id;
    }

}
