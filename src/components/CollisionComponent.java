package components;

import others.Consts;
import others.Entity;
import others.MessageChannel;

public class CollisionComponent implements Component {
    private int bit = Consts.COLLISION;
    private Entity self;

    public CollisionComponent(Entity self) {
        super();
        this.self = self;
    }

    @Override
    public int getBit() {
        return bit;
    }

    public void handleCollision(Entity sender, float x, float y) {

    }

    @Override
    public void process(MessageChannel channel) {

    }

    @Override
    public void receive(String command) {
        // TODO Auto-generated method stub

    }

    @Override
    public void update() {
        // TODO Auto-generated method stub

    }

}
