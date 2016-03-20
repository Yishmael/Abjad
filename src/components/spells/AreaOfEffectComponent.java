package components.spells;

import components.Component;
import others.Consts;
import others.Entity;
import others.MessageChannel;

public class AreaOfEffectComponent implements Component {
    private int id = Consts.AREAOFEFFECT;
    private Entity self;

    private float areaOfEffect;

    public AreaOfEffectComponent(Entity self, float areaOfEffect) {
        this.self = self;
        this.areaOfEffect = areaOfEffect;
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
}
