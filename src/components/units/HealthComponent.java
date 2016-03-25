package components.units;

import org.lwjgl.Sys;

import components.Component;
import others.Consts;
import others.Entity;
import others.MainGame;
import others.MessageChannel;

public class HealthComponent implements Component {
    private long id = Consts.HEALTH;
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

    public void HPdelta(float delta) {
        if (!alive) {
            return;
        }
        if (delta < 0) {
            health = Math.max(health + delta, 0);
            // System.out.println("Damaged to " + health + "/" + maxHealth +
            // "!");
        } else if (delta > 0) {
            health = Math.min(health + delta, maxHealth);
            // System.out.println("Healed to " + health + "/" + maxHealth +
            // "!");
        }
        self.broadcast("updateHP " + health + " " + maxHealth);
        update();
    }

    @Override
    public long getID() {
        return id;
    }

    public boolean isAlive() {
        return alive;
    }

    public float getHealth() {
        return health;
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
        if (str.matches("HPdelta [-]?[0-9]+[.]?[0-9]*")) {
            str = str.substring(8);
            float temp = Float.parseFloat(str);
            HPdelta(temp);
        } else if (str.matches("requestHP")) {
            self.broadcast("updateHP " + health + " " + maxHealth);
        } else if (str.matches("STR [0-9]+")) {
            str = str.substring(4);
            int strength = Integer.parseInt(str);
            percentage = health / maxHealth;
            maxHealth += strength * 5;
            health = percentage * maxHealth;
            self.broadcast("updateHP " + health + " " + maxHealth);
        } else if (str.matches("HPcap [-]?[0-9]+[.]?[0-9]*")) {
            str = str.substring(6);
            percentage = health / maxHealth;
            maxHealth = Math.max(0, maxHealth + Float.parseFloat(str));
            health = percentage * maxHealth;
            self.broadcast("updateHP " + health + " " + maxHealth);
        } else if (str.matches("ress")) {
            if (!alive) {
                health = maxHealth / 2;
                alive = true;
                self.broadcast("updateHP " + health + " " + maxHealth);
                self.broadcast("ressed");
            }
        } else if (str.matches("HPregen [-]?[0-9]+[.]?[0-9]*")) {
            str = str.substring(8);
            healthRegen += Float.parseFloat(str);
        }
    }

    @Override
    public void update() {
        if (health > 0) {
            dt += MainGame.dt;
            if (dt >= 500) {
                dt = 0;
                HPdelta(healthRegen / 2);
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
