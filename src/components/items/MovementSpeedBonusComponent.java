package components.items;

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
    public String[] giveBonuses() {
        return new String[] { "MS " + speedBonus + "%" };
    }

    @Override
    public String[] negateBonuses() {
        return new String[] { "MS " + -speedBonus + "%" };
    }

    public float getMovementBonus() {
        return speedBonus;
    }

    @Override
    public long getID() {
        return id;
    }

}
