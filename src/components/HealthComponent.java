package components;

import others.MessageChannel;

public class HealthComponent implements Component {
    public static int bit = 3;
    private int health, maxHealth;
    private boolean alive;

    public HealthComponent(int health, int maxHealth) {
        this.health = Math.min(health, maxHealth);
        this.maxHealth = maxHealth;
        this.alive = health > 0;
    }

    public void damage(String name, int dmg) {
        if (dmg <= 0 || !alive) {
            return;
        }
        health = Math.max(health - dmg, 0);
        System.out.println("Damaged to " + health + "/" + maxHealth + " by " + name + "!");
        update();
    }

    @Override
    public int getBit() {
        return bit;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void heal(String name, int bonus) {
        if (bonus <= 0 || !alive) {
            return;
        }
        health = Math.min(health + bonus, maxHealth);
        System.out.println("Healed to " + health + "/" + maxHealth + " by " + name + "!");
        update();
    }

    public boolean isAlive() {
        return alive;
    }

    @Override
    public void process(MessageChannel channel) {
        String str = channel.getCommand();
        if (str.length() >= 6) {
            if (str.substring(0, 4).equals("heal")) {
                heal(channel.getSender().getName(), Integer.parseInt(str.substring(5)));
                return;
            }
        }
        if (str.length() >= 8) {
            if (str.substring(0, 6).equals("damage")) { // ???
                damage(channel.getSender().getName(), Integer.parseInt(str.substring(7)));
                return;
            }
        }
    }

    @Override
    public void update() {
        if (health > 0) {
            // System.out.println("I'm alive!");
        } else {
            alive = false;
            System.out.println("You died!");
        }
    }
}
