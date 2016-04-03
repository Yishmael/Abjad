package components.spells;

import components.Component;
import others.Consts;
import others.Entity;
import others.MessageChannel;

public class SpellPeriodicDamageComponent implements Component {
    private long id = Consts.SPELLPERIODICDAMAGE;
    private Entity self;

    private float damage, duration;
    private String damageType;

    public SpellPeriodicDamageComponent(Entity self, float damage, float duration, String damageType) {
        this.self = self;
        this.damage = damage;
        this.duration = duration;
        this.damageType = damageType;
    }

    @Override
    public void process(MessageChannel channel) {

    }

    @Override
    public void receive(String command) {
        String str = command;
        if (str.matches("requestspellperiodicdmg")) {
            self.broadcast("spellperiodicdmg " + damageType + "dmg " + damage / duration + " " + duration);
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
