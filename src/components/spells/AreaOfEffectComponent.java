package components.spells;

import components.Component;
import others.Consts;
import others.Entity;
import others.MessageChannel;

public class AreaOfEffectComponent implements Component {
    private long id = Consts.AREAOFEFFECT;
    private Entity self;

    private float areaOfEffect, amount, duration;
    private String effect;

    public AreaOfEffectComponent(Entity self, float areaOfEffect, String effect, float amount, float duration) {
        this.self = self;
        this.areaOfEffect = areaOfEffect;
        this.effect = effect;
        this.amount = amount;
        this.duration = duration;
    }

    @Override
    public void process(MessageChannel channel) {
        // TODO Auto-generated method stub

    }

    @Override
    public void receive(String command) {
        String str = command;
        if (str.matches("added " + Consts.TRANSFORM)) {
            self.broadcast("setEllipse " + areaOfEffect + " " + areaOfEffect);
        } else if (str.matches("requestaoedata")) {
            self.broadcast("aoedata " + effect + " " + amount + " " + duration);
        }
    }

    public float getAreaOfEffect() {
        return areaOfEffect;
    }

    @Override
    public void update() {
    }

    @Override
    public long getID() {
        return id;
    }
}
