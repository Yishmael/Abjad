package components.spells;

import components.Component;
import others.Consts;
import others.Entity;
import others.MessageChannel;

public class SpellHealComponent implements Component {
    private long id = Consts.SPELLHEAL;
    private Entity self;

    private float healing;

    public SpellHealComponent(Entity self, float healing) {
        this.self = self;
        this.healing = healing;
    }

    @Override
    public void process(MessageChannel channel) {

    }

    @Override
    public void receive(String command) {
        String str = command;
        if (str.matches("requesthealing")) {
            self.broadcast("healing " + healing);
        }
    }

    @Override
    public void update() {

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
