package components.items;

import java.util.ArrayList;

import components.Component;
import components.ItemComponent;
import others.Consts;
import others.Entity;
import others.MessageChannel;

public class ElementalAttackDamageBonusComponent implements Component, ItemComponent {
    private long id = Consts.ELEMENTALATTACKDAMAGEBONUS;
    private Entity self;

    private float arcaneBonus, fireBonus, frostBonus, lightningBonus, poisonBonus, shadowBonus;

    public ElementalAttackDamageBonusComponent(Entity self, float arcaneBonus, float fireBonus, float frostBonus,
            float lightningBonus, float poisonBonus, float shadowBonus) {
        this.self = self;
        this.arcaneBonus = arcaneBonus;
        this.fireBonus = fireBonus;
        this.frostBonus = frostBonus;
        this.lightningBonus = lightningBonus;
        this.poisonBonus = poisonBonus;
        this.shadowBonus = shadowBonus;
    }

    @Override
    public ArrayList<String> giveBonuses() {
        ArrayList<String> list = new ArrayList<String>();
        if (arcaneBonus != 0) {
            list.add("arcane " + arcaneBonus);
        }
        if (fireBonus != 0) {
            list.add("fire " + fireBonus);
        }
        if (frostBonus != 0) {
            list.add("frost " + frostBonus);
        }
        if (lightningBonus != 0) {
            list.add("lightning " + lightningBonus);
        }
        if (poisonBonus != 0) {
            list.add("poison " + poisonBonus);
        }
        if (shadowBonus != 0) {
            list.add("shadow " + shadowBonus);
        }
        // prepending "attackMod" to all strings
        for (int i = 0; i < list.size(); i++) {
            list.set(i, "attackMod " + list.get(i));
        }

        return list;
    }

    @Override
    public ArrayList<String> negateBonuses() {
        ArrayList<String> list = new ArrayList<String>();

        if (arcaneBonus != 0) {
            list.add("arcane " + -arcaneBonus);
        }
        if (fireBonus != 0) {
            list.add("fire " + -fireBonus);
        }
        if (frostBonus != 0) {
            list.add("frost " + -frostBonus);
        }
        if (lightningBonus != 0) {
            list.add("lightning " + -lightningBonus);
        }
        if (poisonBonus != 0) {
            list.add("poison " + -poisonBonus);
        }
        if (shadowBonus != 0) {
            list.add("shadow " + -shadowBonus);
        }

        // prepending "attackMod" to all strings
        for (int i = 0; i < list.size(); i++) {
            list.set(i, "attackMod " + list.get(i));
        }

        return list;
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub

    }

    @Override
    public void receive(String command) {
        // TODO Auto-generated method stub

    }

    @Override
    public void process(MessageChannel channel) {
        // TODO Auto-generated method stub

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
