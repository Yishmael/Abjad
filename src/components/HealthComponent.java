package components;

import others.Consts;
import others.Entity;
import others.MainGame;
import others.MessageChannel;

public class HealthComponent implements Component {
    private int bit = Consts.HEALTH;
    private float health, baseMaxHealth, maxHealth, healthRegen;
    private boolean alive;
    private Entity self;
    private float dt;

    public HealthComponent(Entity self, float health, float baseMaxHealth, float healthRegen) {
        this.self = self;
        this.health = Math.min(health, baseMaxHealth);
        this.maxHealth = baseMaxHealth;
        this.baseMaxHealth = baseMaxHealth;
        this.healthRegen = healthRegen;
        this.alive = health > 0;
    }

    public HealthComponent(Entity self, float health, float baseMaxHealth) {
        this.self = self;
        this.health = Math.min(health, baseMaxHealth);
        this.maxHealth = baseMaxHealth;
        this.maxHealth = baseMaxHealth;
        this.healthRegen = 0;
        this.alive = health > 0;
    }

    public void damage(float dmg) {
        if (dmg <= 0 || !alive) {
            return;
        }
        health = Math.max(health - dmg, 0);
        // System.out.println("Damaged to " + health + "/" + maxHealth + "!");
        self.broadcast("updateHP " + health + " " + maxHealth);
    }

    public void damage(String name, float dmg) {
        if (dmg <= 0 || !alive) {
            return;
        }
        health = Math.max(health - dmg, 0);
        // System.out.println("Damaged to " + health + "/" + maxHealth + " by "
        // + name + "!");
        self.broadcast("updateHP " + health + " " + maxHealth);
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
        self.broadcast("updateHP " + health + " " + maxHealth);
    }

    public void heal(String name, float change) {
        if (change <= 0 || !alive) {
            return;
        }
        health = Math.min(health + change, maxHealth);
        // System.out.println("Healed to " + health + "/" + maxHealth + " by " +
        // name + "!");
        self.broadcast("updateHP " + health + " " + maxHealth);
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
        String[] list = null;
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
            self.broadcast("updateHP " + health + " " + maxHealth);
            return;
        }
        if (str.matches("STATS [0-9]+[.]?[0-9]* [0-9]+[.]?[0-9]* [0-9]+[.]?[0-9]*")) {
            str = str.substring(6);
            list = str.split(" ");
            float strength = Float.parseFloat(list[0]);
            if (strength != 0) {
                maxHealth = strength * 10 + baseMaxHealth + Math.min(10000, (float) Math.pow(1.17f, strength));
                self.broadcast("updateHP " + health + " " + maxHealth);
            }
            return;
        }
    }

    @Override
    public void update() {
        if (health > 0) {
            dt += MainGame.dt;
            if (dt >= 500) {
                dt = 0;
                if (healthRegen > 0 && health < maxHealth) {
                    heal(healthRegen / 2);
                    self.broadcast("updateHP " + health + " " + maxHealth);
                } else if (healthRegen < 0) {
                    damage(-healthRegen / 2);
                    self.broadcast("updateHP " + health + " " + maxHealth);
                }
            }
            // System.out.println("I'm alive!");
        } else {
            if (alive) {
                alive = false;
                self.broadcast("died");
                healthRegen = 0;
            }
        }
    }
}
