package components;

import others.Consts;
import others.Entity;
import others.MainGame;
import others.MessageChannel;

public class ManaComponent implements Component {
    private int bit = Consts.MANA;
    private float mana, maxMana, manaRegen, manaOnDeath = 0;
    private Entity self;
    private float dt = 0;

    public ManaComponent(Entity self, float mana, float maxMana, float manaRegen) {
        this.self = self;
        this.mana = Math.min(mana, maxMana);
        this.maxMana = maxMana;
        this.manaRegen = manaRegen;
    }

    public void drain(float change) {
        if (change > 0) {
            mana = Math.max(mana - change, 0);
            // System.out.println("Drained to " + mana + "/" + maxMana + "!");
        }
        update();
    }

    @Override
    public int getBit() {
        return bit;
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
        if (str.matches("requestMP")) {
            update();
        }
    }

    @Override
    public void receive(String command) {
        String str = command;
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
            update();
        }
    }

    public void replenish(float change) {
        if (change > 0) {
            mana = Math.min(mana + change, maxMana);
            // System.out.println("Replenished to " + mana + "/" + maxMana +
            // "!");
        }
        update();
    }

    @Override
    public void update() {
        dt += MainGame.dt;
        if (dt >= 100) {
            dt = 0;
            if (mana < maxMana) {
                replenish(manaRegen / 10);
            }
        }
        self.broadcast("updateMP " + mana + " " + maxMana);
    }
}
