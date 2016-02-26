package components;

import others.Consts;
import others.Entity;
import others.MessageChannel;

public class CameraComponent implements Component {
    private int bit = Consts.CAMERA;
    private Entity self;

    public CameraComponent(Entity self) {
        this.self = self;
    }

    @Override
    public int getBit() {
        // TODO Auto-generated method stub
        return 0;
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

}
