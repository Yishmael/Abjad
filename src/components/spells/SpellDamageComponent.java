package components.spells;

import components.Component;
import others.Consts;
import others.Entity;
import others.MessageChannel;

public class SpellDamageComponent implements Component {
    private int id = Consts.SPELLDAMAGE;
    private Entity self;

    private float damage;
    private String damageType;

    public SpellDamageComponent(Entity self, float damage, String damageType) {
        this.self = self;
        this.damage = damage;
        this.damageType = damageType;
    }

    @Override
    public void process(MessageChannel channel) {

    }

    @Override
    public void receive(String command) {

    }

    public String getMessage() {
        self.broadcast("finished");
        return damageType + "dmg " + damage;
    }

    @Override
    public void update() {

    }

    @Override
    public int getID() {
        return id;
    }
}
