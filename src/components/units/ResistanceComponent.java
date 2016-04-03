package components.units;

import components.Component;
import others.Consts;
import others.Entity;
import others.MessageChannel;

public class ResistanceComponent implements Component {
    private long id = Consts.RESISTANCE;
    private Entity self;

    private float arcaneResistance, fireResistance, frostResistance, lightningResistance, poisonResistance,
            shadowResistance;

    public ResistanceComponent(Entity self, float arcaneResistance, float fireResistance, float frostResistance,
            float lightningResistance, float poisonResistance, float shadowResistance) {
        this.self = self;
        this.arcaneResistance = arcaneResistance;
        this.fireResistance = fireResistance;
        this.frostResistance = frostResistance;
        this.lightningResistance = lightningResistance;
        this.poisonResistance = poisonResistance;
        this.shadowResistance = shadowResistance;
    }

    public ResistanceComponent(Entity self) {
        this.self = self;
        this.arcaneResistance = 0;
        this.fireResistance = 0;
        this.frostResistance = 0;
        this.lightningResistance = 0;
        this.poisonResistance = 0;
        this.shadowResistance = 0;
    }

    @Override
    public void process(MessageChannel channel) {
        if (channel != null) {
            receive(channel.getCommand());
        }
    }

    @Override
    public void receive(String command) {
        String str = command;
        if (str.matches("[a-z]+dmg [0-9]+[.]?[0-9]*")) {
            String damageType = str.substring(0, str.indexOf("dmg"));
            str = str.substring(damageType.length() + 4);
            float temp = Float.parseFloat(str);
            if (damageType.matches("arcane")) {
                temp *= 1 - getArcaneResistance() / 100;
            } else if (damageType.matches("fire")) {
                temp *= 1 - getFireResistance() / 100;
            } else if (damageType.matches("frost")) {
                temp *= 1 - getFrostResistance() / 100;
            } else if (damageType.matches("lightning")) {
                temp *= 1 - getLightningResistance() / 100;
            } else if (damageType.matches("poison")) {
                temp *= 1 - getPoisonResistance() / 100;
            } else if (damageType.matches("shadow")) {
                temp *= 1 - getShadowResistance() / 100;
            } else {
                return;
            }
            self.broadcast("HPdelta " + -temp);
        } else if (str.matches("arcaneres [0-9]+[.]?[0-9]*")) {
            str = str.substring(10);
            arcaneResistance += Float.parseFloat(str);
        } else if (str.matches("fireres [0-9]+[.]?[0-9]*")) {
            str = str.substring(8);
            fireResistance += Float.parseFloat(str);
        } else if (str.matches("frostres [0-9]+[.]?[0-9]*")) {
            str = str.substring(9);
            frostResistance += Float.parseFloat(str);
        } else if (str.matches("lightningres [0-9]+[.]?[0-9]*")) {
            str = str.substring(13);
            lightningResistance += Float.parseFloat(str);
        } else if (str.matches("poisonres [0-9]+[.]?[0-9]*")) {
            str = str.substring(10);
            poisonResistance += Float.parseFloat(str);
        } else if (str.matches("shadowres [0-9]+[.]?[0-9]*")) {
            str = str.substring(10);
            shadowResistance += Float.parseFloat(str);
        }
    }

    public float getArcaneResistance() {
        return arcaneResistance;
    }

    public float getFireResistance() {
        return fireResistance;
    }

    public float getFrostResistance() {
        return frostResistance;
    }

    public float getLightningResistance() {
        return lightningResistance;
    }

    public float getPoisonResistance() {
        return poisonResistance;
    }

    public float getShadowResistance() {
        return shadowResistance;
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
