package components.items;

import components.Component;
import components.ItemComponent;
import others.Consts;
import others.Entity;
import others.MessageChannel;

public class LifestealComponent implements Component, ItemComponent {
    private long id = Consts.LIFESTEAL;
    private Entity self;

    private float lifesteal;

    public LifestealComponent(Entity self, float lifesteal) {
        this.self = self;
        this.lifesteal = lifesteal;
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub

    }

    @Override
    public void receive(String command) {
    }

    @Override
    public void process(MessageChannel channel) {
        // TODO Auto-generated method stub

    }

    @Override
    public String[] giveBonuses() {
        return new String[] {"LS " + lifesteal + "%"};
    }

    @Override
    public String[] negateBonuses() {
        return new String[] {"LS " + -lifesteal + "%"};
    }

    @Override
    public long getID() {
        return id;
    }

}
