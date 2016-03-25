package components.items;

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
    public String[] giveBonuses() {
        return new String[] {"HPcap " + healthBonus};
    }

    @Override
    public String[] negateBonuses() {
        return new String[] {"HPcap " + -healthBonus};
    }

    public float getHealthBonus() {
        return healthBonus;
    }

    @Override
    public long getID() {
        return id;
    }

}
