package components.items;

import components.Component;
import others.Consts;
import others.Entity;
import others.MessageChannel;

public class MovementSpeedBonusComponent implements Component, ItemComponent {
    private int id = Consts.MOVEMENTBONUS;
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
    public String giveBonus() {
        return "MS " + speedBonus + "%";
    }

    @Override
    public String negateBonus() {
        return "MS " + -speedBonus + "%";
    }

    public float getMovementBonus() {
        return speedBonus;
    }

    @Override
    public int getID() {
        return id;
    }

}
