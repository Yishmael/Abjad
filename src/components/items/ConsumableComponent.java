package components.items;

import components.Component;
import others.Consts;
import others.Entity;
import others.MessageChannel;

public class ConsumableComponent implements Component, ItemComponent {
    private int id = Consts.CONSUMABLE;
    private Entity self;

    private float amount, duration;
    private String effect;

    public ConsumableComponent(Entity self, float amount, String effect, float duration) {
        this.self = self;
        this.amount = amount;
        this.effect = effect;
        this.duration = duration;
    }

    @Override
    public void process(MessageChannel channel) {
        // TODO Auto-generated method stub

    }

    @Override
    public void receive(String command) {
        // TODO Auto-generated method stub

    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public String giveBonus() {
        return effect + " " + amount / duration + " " + duration;
    }

    @Override
    public String negateBonus() {
        return null;
    }

}
