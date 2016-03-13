package components;

import org.lwjgl.Sys;

import others.Consts;
import others.Entity;
import others.MainGame;
import others.MessageChannel;

public class HealthComponent implements Component {
    private int id = Consts.HEALTH;
    private float health, baseMaxHealth, maxHealth, healthRegen, percentage;
    private boolean alive;
    private Entity self;
    private float dt;
    private long timeOfDeath;

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
        this.baseMaxHealth = baseMaxHealth;
        this.healthRegen = 0;
        this.alive = health > 0;
    }

    public HealthComponent(Entity self, float baseMaxHealth) {
        this.self = self;
        this.health = baseMaxHealth;
        this.maxHealth = baseMaxHealth;
        this.baseMaxHealth = baseMaxHealth;
        this.healthRegen = 0;
        this.alive = health > 0;
    }

    public void damage(float amount) {
        if (amount <= 0 || !alive) {
            return;
        }
        health = Math.max(health - amount, 0);
        // System.out.println("Damaged to " + health + "/" + maxHealth + "!");
        self.broadcast("updateHP " + health + " " + maxHealth);
        update();
    }

    public void damage(String name, float amount) {
        if (amount <= 0 || !alive) {
            return;
        }
        health = Math.max(health - amount, 0);
        // System.out.println("Damaged to " + health + "/" + maxHealth + " by "
        // + name + "!");
        self.broadcast("updateHP " + health + " " + maxHealth);
        update();
    }

    @Override
    public int getID() {
        return id;
    }

    public void heal(float amount) {
        if (amount <= 0 || !alive) {
            return;
        }
        health = Math.min(health + amount, maxHealth);
        // System.out.println("Healed to " + health + "/" + maxHealth + "!");
        self.broadcast("updateHP " + health + " " + maxHealth);
    }

    public void heal(String name, float amount) {
        if (amount <= 0 || !alive) {
            return;
        }
        health = Math.min(health + amount, maxHealth);
        // System.out.println("Healed to " + health + "/" + maxHealth + " by " +
        // name + "!");
        self.broadcast("updateHP " + health + " " + maxHealth);
    }

    public boolean isAlive() {
        return alive;
    }

    public long getTimeOfDeath() {
        return timeOfDeath;
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
            percentage = health / maxHealth;
            maxHealth = Math.min(999, strength * 10 + baseMaxHealth + (float) Math.pow(1.17f, strength) - 1);
            health = percentage * maxHealth;
            self.broadcast("updateHP " + health + " " + maxHealth);
            return;
        }
        if (str.matches("HPcap [-]?[0-9]+[.]?[0-9]*")) {
            str = str.substring(6);
            percentage = health / maxHealth;
            maxHealth = Math.min(999, maxHealth + Float.parseFloat(str));
            health = percentage * maxHealth;
            self.broadcast("updateHP " + health + " " + maxHealth);
            return;
        }
        if (str.matches("ress")) {
            if (!alive) {
                health = maxHealth / 2;
                alive = true;
                self.broadcast("updateHP " + health + " " + maxHealth);
                self.broadcast("ressed");
            }
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
                } else if (healthRegen < 0) {
                    damage(-healthRegen / 2);
                }
            }
            // System.out.println("I'm alive!");
        } else {
            if (alive) {
                alive = false;
                timeOfDeath = (Sys.getTime() * 1000) / Sys.getTimerResolution();
                self.broadcast("died");
            }
        }
    }
}
