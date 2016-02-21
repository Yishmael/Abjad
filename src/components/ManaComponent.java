package components;

import others.MessageChannel;

public class ManaComponent implements Component {
    public static int bit = 9;
    private int mana, maxMana;

    public ManaComponent(int mana, int maxMana) {
        this.mana = Math.min(mana, maxMana);
        this.maxMana = maxMana;
    }

    @Override
    public int getBit() {
        return bit;
    }

    public int getMaxMana() {
        return maxMana;
    }

    @Override
    public void process(MessageChannel channel) {
        if (channel.getSender() == null) {
            return;
        }
        String str = channel.getCommand();
        if (str.length() >= 11) {
            if (str.substring(0, 9).equals("replenish")) {
                replenish(channel.getSender().getName(), Integer.parseInt(str.substring(10)));
                return;
            }
        }
    }

    public void replenish(String name, int bonus) {
        if (bonus > 0) {
            mana = Math.min(mana + bonus, maxMana);
            System.out.println("Replenished to " + mana + "/" + maxMana + " by " + name + "!");
        } else if (bonus < 0) {
            mana = Math.max(mana + bonus, 0);
            System.out.println("Drained to " + mana + "/" + maxMana + " by " + name + "!");
        }
        update();
    }

    @Override
    public void update() {
    }
}
