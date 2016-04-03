package components.units;

import components.Component;
import others.Consts;
import others.Entity;
import others.MessageChannel;

public class CameraComponent implements Component {
    private long id = Consts.CAMERA;
    private Entity self;

    public CameraComponent(Entity self) {
        this.self = self;
    }

    @Override
    public long getID() {
        return id;
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
    public void draw() {
        // TODO Auto-generated method stub

    }

}
