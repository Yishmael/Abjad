package components.items;

import components.Component;
import components.ItemComponent;
import others.Consts;
import others.Entity;
import others.MessageChannel;

public class WeaponComponent implements Component, ItemComponent {
    private long id = Consts.WEAPON;
    private Entity self;
    private float damage, cooldown;
    private int rangeAdder;

    public WeaponComponent(Entity self, float damage, float cooldown, int rangeAdder) {
        this.self = self;
        this.damage = damage;
        this.cooldown = cooldown;
        this.rangeAdder = rangeAdder;
    }

    @Override
    public void process(MessageChannel channel) {

    }

    @Override
    public void receive(String command) {

    }

    @Override
    public void update() {

    }

    @Override
    public String[] giveBonuses() {
        // letting attack component know which weapon was equipped
        return new String[] { "equippedW " + damage + " " + cooldown + " " + rangeAdder };
    }

    @Override
    public String[] negateBonuses() {
        // letting attack component know which weapon was unequipped
        return new String[] { "unequippedW" };
    }

    public float getDamage() {
        return damage;
    }

    public float getCooldown() {
        return cooldown;
    }

    public int getRangeAdder() {
        return rangeAdder;
    }

    @Override
    public long getID() {
        return id;
    }

}
