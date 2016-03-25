package components.items;

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
    public String[] giveBonuses() {
        return new String[] { "MPcap " + manaBonus };
    }

    @Override
    public String[] negateBonuses() {
        return new String[] { "MPcap " + -manaBonus };
    }

    public float getManaBonus() {
        return manaBonus;
    }

    @Override
    public long getID() {
        return id;
    }

}
