package components.spells;

import org.lwjgl.Sys;

import components.Component;
import others.Consts;
import others.Entity;
import others.MessageChannel;

public class DurationComponent implements Component {
    private long id = Consts.DURATION;
    private Entity self;

    private float duration;

    private long createTime;

    public DurationComponent(Entity self, float duration) {
        this.self = self;
        this.duration = duration;
        createTime = (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    @Override
    public void update() {
        if ((Sys.getTime() * 1000) / Sys.getTimerResolution() - createTime >= duration * 1000) {
            self.broadcast("finished");
        }
    }

    @Override
    public void receive(String command) {

    }

    @Override
    public void process(MessageChannel channel) {

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
