package components;

import org.lwjgl.Sys;

import others.Consts;
import others.Entity;
import others.MessageChannel;

public class SpellComponent implements Component {
    private int bit = Consts.SPELL;
    private float manaCost, damage, cooldown, lastMana = 0;
    private boolean alive;
    private Entity self;
    private long lastTime = 0;
    private long now = 0;
    private int currentSpell = 0;

    public SpellComponent(Entity self, float manaCost, float damage, float cooldown) {
        this.self = self;
        this.manaCost = manaCost;
        this.damage = damage;
        this.cooldown = cooldown;
    }

    public String fireball() {
        now = (Sys.getTime() * 1000) / Sys.getTimerResolution();
        if (now - lastTime >= 1000 * cooldown) {
            lastTime = now;
            self.broadcast("requestMP");
            if (manaCost <= lastMana) {
                self.broadcast("drain " + manaCost);
                // self.broadcast("damage " + damage);
                // System.out.println("Casting successful!");
                self.broadcast("animateFireball");
                return "damage " + damage;
            } else {
                System.out.println("Need " + (int) (manaCost - lastMana) + " more mana for Fireball!");
            }
        }
        return null;
    }

    public String nourish() {
        now = (Sys.getTime() * 1000) / Sys.getTimerResolution();
        if (now - lastTime >= 1000 * cooldown) {
            lastTime = now;
            self.broadcast("requestMP");
            if (manaCost <= lastMana) {
                self.broadcast("drain " + manaCost);
                self.broadcast("heal " + damage);
                self.broadcast("animateNourish");
            } else {
                System.out.println("Need " + (int) (manaCost - lastMana) + " more mana for Nourish!");
            }
        }
        return null;
    }

    public String explosion() {
        now = (Sys.getTime() * 1000) / Sys.getTimerResolution();
        if (now - lastTime >= 1000 * cooldown) {
            lastTime = now;
            self.broadcast("requestMP");
            if (lastMana > 0) {
                self.broadcast("drain " + lastMana);
                self.broadcast("damage " + lastMana);
                self.broadcast("animateExplosion");
                return "damage " + lastMana;
            }
        }
        return null;
    }

    @Override
    public int getBit() {
        return bit;
    }

    public boolean isAlive() {
        return alive;
    }

    @Override
    public void process(MessageChannel channel) {

    }

    public String cast() {
        if (currentSpell == 0) {
            return fireball();
        } else if (currentSpell == 1) {
            return nourish();
        } else if (currentSpell == 2) {
            return explosion();
        }
        return null;
    }

    @Override
    public void receive(String command) {
        String str = command;
        String[] list = null;
        if (str.matches("next spell")) {
            currentSpell++;
            currentSpell %= 3;
            if (currentSpell == 0) {
                System.out.println("Fireball");
            } else if (currentSpell == 1) {
                System.out.println("Nourish");
            } else if (currentSpell == 2) {
                System.out.println("Explosion");
            }
            return;
        }
        if (str.matches("updateMP [0-9]+[.]?[0-9]* [0-9]+[.]?[0-9]*")) {
            str = str.substring(9);
            list = str.split(" ");
            lastMana = Float.parseFloat(list[0]);
            return;
        }
    }

    @Override
    public void update() {

    }
}
