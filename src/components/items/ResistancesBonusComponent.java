package components.items;

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
    public String[] giveBonuses() {
        String[] temp = new String[6];
        int i = 0;
        if (arcaneResistance != 0) {
            temp[i++] = "arcaneres " + arcaneResistance;
        }
        if (fireResistance != 0) {
            temp[i++] = "fireres " + fireResistance;
        }
        if (frostResistance != 0) {
            temp[i++] = "frostres " + frostResistance;
        }
        if (lightningResistance != 0) {
            temp[i++] = "lightningres " + lightningResistance;
        }
        if (poisonResistance != 0) {
            temp[i++] = "poisonres " + poisonResistance;
        }
        if (shadowResistance != 0) {
            temp[i++] = "shadowres " + shadowResistance;
        }

        // returning only those strings whose bonuses are not zero
        String bonuses[] = new String[i];
        for (int j = 0; j < i; j++) {
            bonuses[j] = temp[j];
        }

        return bonuses;
    }

    @Override
    public String[] negateBonuses() {
        return new String[] { "arcaneres " + -arcaneResistance, "fireres " + -fireResistance,
                "frostres " + -frostResistance, "lightningres " + -lightningResistance,
                "poisonres " + -poisonResistance, "shadowres " + -shadowResistance };
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

}
