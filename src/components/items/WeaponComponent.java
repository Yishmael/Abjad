package components.items;

import components.Component;
import others.Consts;
import others.Entity;
import others.MessageChannel;

public class WeaponComponent implements Component, ItemComponent {
    private int id = Consts.WEAPON;
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
    public String giveBonus() {
        return "equippedW " + damage + " " + cooldown + " " + rangeAdder;
    }

    @Override
    public String negateBonus() {
        return null;
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
    public int getID() {
        return id;
    }

}
