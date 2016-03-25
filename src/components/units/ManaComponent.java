package components.units;

import components.Component;
import others.Consts;
import others.Entity;
import others.MainGame;
import others.MessageChannel;

public class ManaComponent implements Component {
    private long id = Consts.MANA;
    private float mana, baseMaxMana, maxMana, manaRegen, manaOnDeath = 0, percentage;
    private boolean canRegen = true;
    private Entity self;
    private float dt = 0;

    public ManaComponent(Entity self, float mana, float baseMaxMana, float manaRegen) {
        this.self = self;
        this.mana = Math.min(mana, baseMaxMana);
        this.baseMaxMana = baseMaxMana;
        this.maxMana = baseMaxMana;
        this.manaRegen = manaRegen;
    }

    public ManaComponent(Entity self, float mana, float baseMaxMana) {
        this.self = self;
        this.mana = Math.min(mana, baseMaxMana);
        this.baseMaxMana = baseMaxMana;
        this.maxMana = baseMaxMana;
        this.manaRegen = 0;
    }
    public ManaComponent(Entity self, float baseMaxMana) {
        this.self = self;
        this.mana = baseMaxMana;
        this.baseMaxMana = baseMaxMana;
        this.maxMana = baseMaxMana;
        this.manaRegen = 0;
    }

    public void MPdelta(float delta) {
        if (delta < 0) {
            mana = Math.max(mana + delta, 0);
            // System.out.println("Burned to " + mana + "/" + maxMana + "!");
        } else if (delta > 0) {
            mana = Math.min(mana + delta, maxMana);
            // System.out.println("Replenished to " + mana + "/" + maxMana +
            // "!");
        }
        self.broadcast("updateMP " + mana + " " + maxMana);
        update();
    }

    public float getManaOnDeath() {
        return manaOnDeath;
    }

    public float getMana() {
        return mana;
    }

    @Override
    public void process(MessageChannel channel) {
        if (channel.getSender() == null) {
            return;
        }
        receive(channel.getCommand());
    }

    @Override
    public void receive(String command) {
        String str = command;

        if (str.matches("MPdelta [-]?[0-9]+[.]?[0-9]*")) {
            str = str.substring(8);
            float temp = Float.parseFloat(str);
            MPdelta(temp);
            return;
        }
        if (str.matches("died")) {
            manaOnDeath = mana;
            canRegen = false;
            // System.out.println("Mana on death: " + manaOnDeath);
            return;
        }
        if (str.matches("ressed")) {
            manaOnDeath = 0;
            canRegen = true;
            return;
        }
        if (str.matches("requestMP")) {
            self.broadcast("updateMP " + mana + " " + maxMana);
            return;
        }
        if (str.matches("INT [0-9]+")) {
            str = str.substring(4);
            int intelligence = Integer.parseInt(str);
            percentage = mana / maxMana;
            maxMana += intelligence * 3;
            mana = percentage * maxMana;
            self.broadcast("updateMP " + mana + " " + maxMana);
            return;
        }
        if (str.matches("MPregen [-]?[0-9]+[.]?[0-9]*")) {
            str = str.substring(8);
            manaRegen += Float.parseFloat(str);
            return;
        }
        if (str.matches("MPcap [-]?[0-9]+[.]?[0-9]*")) {
            str = str.substring(6);
            percentage = mana / maxMana;
            maxMana = Math.max(0, maxMana + Float.parseFloat(str));
            mana = percentage * maxMana;
            self.broadcast("updateMP " + mana + " " + maxMana);
            return;
        }
    }

    @Override
    public void update() {
        if (canRegen) {
            dt += MainGame.dt;
            if (dt >= 500) {
                dt = 0;
                MPdelta(manaRegen / 2);
            }
        }
    }

    @Override
    public long getID() {
        return id;
    }
}
