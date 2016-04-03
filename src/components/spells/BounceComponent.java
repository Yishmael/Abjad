package components.spells;

import components.Component;
import others.Consts;
import others.Entity;
import others.MessageChannel;

public class BounceComponent implements Component {
    private long id = Consts.BOUNCE;
    private Entity self;

    private int numberOfBounces;
    private int remainingBounces;

    public BounceComponent(Entity self, int numberOfBounces) {
        this.self = self;
        this.numberOfBounces = numberOfBounces;
        this.remainingBounces = numberOfBounces;
    }

    @Override
    public void process(MessageChannel channel) {
        if (channel.getSender() == null) {
            return;
        }
    }

    @Override
    public void receive(String command) {
        String str = command;
        if (str.matches("bounced")) {
            remainingBounces--;
            if (remainingBounces < 0) {
                self.broadcast("bouncingstop");
            }
            if (numberOfBounces - remainingBounces == 1) {
                self.broadcast("angle -1");
                self.broadcast("healing 6");
            }
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
