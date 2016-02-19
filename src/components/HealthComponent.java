package components;

public class HealthComponent implements Component {
    private int currentHealth, maxHealth;
    private boolean alive;

    public HealthComponent(int currentHealth, int maxHealth) {
        if (currentHealth > maxHealth) {
            this.currentHealth = maxHealth;
            this.maxHealth = maxHealth;
        } else {
            this.currentHealth = currentHealth;
            this.maxHealth = maxHealth;
        }
        alive = currentHealth > 0;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    @Override
    public void update() {
        if (currentHealth > 0) {
            // System.out.println("I'm alive!");
        } else {
            alive = false;
            // System.out.println("Dead!");
        }
    }

    public boolean isAlive() {
        return alive;
    }
}
