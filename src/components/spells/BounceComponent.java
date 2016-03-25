package components.spells;

import components.Component;
import others.Consts;
import others.Entity;
import others.MessageChannel;

public class BounceComponent implements Component{
    private long id = Consts.BOUNCE;
    private Entity self;

    public BounceComponent(Entity self, float speed, float cycles, float radius) {
    }


    @Override
    public void process(MessageChannel channel) {
        if (channel.getSender() == null) {
            return;
        }
    }

    @Override
    public void receive(String command) {
    }

    @Override
    public void update() {
    }

    @Override
    public long getID() {
        return id;
    }
}
