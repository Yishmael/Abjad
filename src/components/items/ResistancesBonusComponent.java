package components.items;

import java.util.ArrayList;

import components.Component;
import components.ItemComponent;
import others.Consts;
import others.Entity;
import others.MessageChannel;

public class ResistancesBonusComponent implements Component, ItemComponent {
    private long id = Consts.RESISTANCEBONUS;
    private Entity self;

    private float arcaneResistance, fireResistance, frostResistance, lightningResistance, poisonResistance,
            shadowResistance;

    public ResistancesBonusComponent(Entity self, float arcaneResistance, float fireResistance, float frostResistance,
            float lightningResistance, float poisonResistance, float shadowResistance) {
        this.self = self;
        this.arcaneResistance = arcaneResistance;
        this.fireResistance = fireResistance;
        this.frostResistance = frostResistance;
        this.lightningResistance = lightningResistance;
        this.poisonResistance = poisonResistance;
        this.shadowResistance = shadowResistance;
    }

    @Override
    public ArrayList<String> giveBonuses() {
        ArrayList<String> list = new ArrayList<String>();
        if (arcaneResistance != 0) {
            list.add("arcaneres " + arcaneResistance);
        }
        if (fireResistance != 0) {
            list.add("fireres " + fireResistance);
        }
        if (frostResistance != 0) {
            list.add("frostres " + frostResistance);
        }
        if (lightningResistance != 0) {
            list.add("lightningres " + lightningResistance);
        }
        if (poisonResistance != 0) {
            list.add("poisonres " + poisonResistance);
        }
        if (shadowResistance != 0) {
            list.add("shadowres " + shadowResistance);
        }

        return list;
    }

    @Override
    public ArrayList<String> negateBonuses() {
        ArrayList<String> list = new ArrayList<String>();
        if (arcaneResistance != 0) {
            list.add("arcaneres " + -arcaneResistance);
        }
        if (fireResistance != 0) {
            list.add("fireres " + -fireResistance);
        }
        if (frostResistance != 0) {
            list.add("frostres " + -frostResistance);
        }
        if (lightningResistance != 0) {
            list.add("lightningres " + -lightningResistance);
        }
        if (poisonResistance != 0) {
            list.add("poisonres " + -poisonResistance);
        }
        if (shadowResistance != 0) {
            list.add("shadowres " + -shadowResistance);
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
