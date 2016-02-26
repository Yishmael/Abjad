package components;

import others.Consts;
import others.Entity;
import others.MainGame;
import others.MessageChannel;

public class HealthComponent implements Component {
    private int bit = Consts.HEALTH;
    private float health, maxHealth, healthRegen;
    private boolean alive;
    private Entity self;
    private float dt;

    public HealthComponent(Entity self, float health, float maxHealth, float healthRegen) {
        this.self = self;
        this.health = Math.min(health, maxHealth);
        this.maxHealth = maxHealth;
        this.healthRegen = healthRegen;
        this.alive = health > 0;
    }

    public void damage(float dmg) {
        if (dmg <= 0 || !alive) {
            return;
        }
        health = Math.max(health - dmg, 0);
        // System.out.println("Damaged to " + health + "/" + maxHealth + "!");
        update();
    }

    public void damage(String name, float dmg) {
        if (dmg <= 0 || !alive) {
            return;
        }
        health = Math.max(health - dmg, 0);
//        System.out.println("Damaged to " + health + "/" + maxHealth + " by " + name + "!");
        update();
    }

    @Override
    public int getBit() {
        return bit;
    }

    public void heal(float change) {
        if (change <= 0 || !alive) {
            return;
        }
        health = Math.min(health + change, maxHealth);
        // System.out.println("Healed to " + health + "/" + maxHealth + "!");
        update();
    }

    public void heal(String name, float change) {
        if (change <= 0 || !alive) {
            return;
        }
        health = Math.min(health + change, maxHealth);
//        System.out.println("Healed to " + health + "/" + maxHealth + " by " + name + "!");
        update();
    }

    public boolean isAlive() {
        return alive;
    }

    @Override
    public void process(MessageChannel channel) {
        if (channel.getSender() == null) {
            return;
        }
        String str = channel.getCommand();
        if (str.matches("heal [0-9]+[.]?[0-9]*")) {
            str = str.substring(5);
            heal(channel.getSender().getName(), Float.parseFloat(str));
            return;
        }
        if (str.matches("damage [0-9]+[.]?[0-9]*")) {
            str = str.substring(7);
            damage(channel.getSender().getName(), Float.parseFloat(str));
            return;
        }
    }

    @Override
    public void receive(String command) {
        String str = command;
        if (str.matches("heal [0-9]+[.]?[0-9]*")) {
            str = str.substring(5);
            heal(Float.parseFloat(str));
            return;
        }
        if (str.matches("damage [0-9]+[.]?[0-9]*")) {
            str = str.substring(7);
            damage(Float.parseFloat(str));
            return;
        }
        if (str.matches("requestHP")) {
            update();
            return;
        }
    }

    @Override
    public void update() {
        dt += MainGame.dt;
        if (dt >= 100) {
            dt = 0;
            if (health < maxHealth) {
                heal(healthRegen/10);
            }
        }
        if (health > 0) {
            // System.out.println("I'm alive!");
        } else {
            alive = false;
            healthRegen = 0;
            self.broadcast("died");
        }
        self.broadcast("updateHP " + health + " " + maxHealth);
    }
}
