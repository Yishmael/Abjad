package components;

import others.Consts;
import others.Entity;
import others.MainGame;
import others.MessageChannel;

public class ManaComponent implements Component {
    private int bit = Consts.MANA;
    private float mana, baseMaxMana, maxMana, manaRegen, manaOnDeath = 0;
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
        this.mana = Math.min(mana, maxMana);
        this.baseMaxMana = baseMaxMana;
        this.maxMana = baseMaxMana;
        this.manaRegen = 0;
    }

    public void drain(float change) {
        if (change > 0) {
            mana = Math.max(mana - change, 0);
            // System.out.println("Drained to " + mana + "/" + maxMana + "!");
        }
        update();
    }

    public void replenish(float change) {
        if (change > 0) {
            mana = Math.min(mana + change, maxMana);
            // System.out.println("Replenished to " + mana + "/" + maxMana +
            // "!");
        }
        self.broadcast("updateMP " + mana + " " + maxMana);
    }

    public float getManaOnDeath() {
        return manaOnDeath;
    }

    @Override
    public void process(MessageChannel channel) {
        String str = channel.getCommand();
        if (str.matches("drain [0-9]+[.]?[0-9]*")) {
            str = str.substring(6);
            drain(Float.parseFloat(str));
            return;
        }
        if (str.matches("died")) {
            manaOnDeath = mana;
            // System.out.println("Mana on death: " + manaOnDeath);
            return;
        }
    }

    @Override
    public void receive(String command) {
        String str = command;
        String[] list = null;
        if (str.matches("drain [0-9]+[.]?[0-9]*")) {
            str = str.substring(6);
            drain(Float.parseFloat(str));
            return;
        }
        if (str.matches("died")) {
            manaOnDeath = mana;
            manaRegen = 0;
            // System.out.println("Mana on death: " + manaOnDeath);
            return;
        }
        if (str.matches("requestMP")) {
            self.broadcast("updateMP " + mana + " " + maxMana);
            return;
        }
        if (str.matches("STATS [0-9]+[.]?[0-9]* [0-9]+[.]?[0-9]* [0-9]+[.]?[0-9]*")) {
            str = str.substring(6);
            list = str.split(" ");
            float intelligence = Float.parseFloat(list[2]);
            if (intelligence != 0) {
                maxMana = intelligence * 10 + baseMaxMana + Math.min(10000, (float) Math.pow(1.25f, intelligence));
                self.broadcast("updateMP " + mana + " " + maxMana);
            }
            return;
        }
    }

    @Override
    public void update() {
        dt += MainGame.dt;
        if (dt >= 500) {
            dt = 0;
            if (manaRegen > 0 && mana < maxMana) {
                replenish(manaRegen / 2);
                self.broadcast("updateMP " + mana + " " + maxMana);
            } else if (manaRegen < 0) {
                drain(-manaRegen / 2);
                self.broadcast("updateMP " + mana + " " + maxMana);
            }
        }
    }

    @Override
    public int getBit() {
        return bit;
    }
}
