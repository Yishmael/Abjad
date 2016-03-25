package components.items;

import components.Component;
import components.ItemComponent;
import enums.StatusType;
import others.Consts;
import others.Entity;
import others.MessageChannel;

public class ConsumableComponent implements Component, ItemComponent {
    private long id = Consts.CONSUMABLE;
    private Entity self;

    private StatusType[] status;
    private float amount[], duration;

    public ConsumableComponent(Entity self, StatusType[] status, float[] amount, float duration) {
        this.self = self;
        this.status = new StatusType[status.length];
        this.status = status;
        this.amount = new float[amount.length];
        this.amount = amount;
        this.duration = duration;
    }

    public ConsumableComponent(Entity self, StatusType status, float amount, float duration) {
        this.self = self;
        this.status = new StatusType[1];
        this.status[0] = status;
        this.amount = new float[1];
        this.amount[0] = amount;
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
    public long getID() {
        return id;
    }

    @Override
    public String[] giveBonuses() {
        if (duration > 0) {
            String[] total = new String[status.length];
            for (int i = 0; i < total.length; i++) {
                total[i] = status[i].name() + " " + (amount[i] / duration) + " " + duration;
            }
            return total;
        }
        return null;
    }

    @Override
    public String[] negateBonuses() {
        return null;
    }

}
