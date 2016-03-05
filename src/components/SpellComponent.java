package components;

import org.lwjgl.Sys;

import enums.SpellType;
import others.Consts;
import others.Entity;
import others.MessageChannel;

public class SpellComponent implements Component {
    private int bit = Consts.SPELL;
    private float manaCost, damage, healing, cooldown, lastMana = 0;
    private Entity self;
    private long lastTime = 0;
    private long now = 0;
    private SpellType currentSpell = SpellType.values()[0];

    public SpellComponent(Entity self, float manaCost, float damage, float cooldown) {
        this.self = self;
        this.manaCost = manaCost;
        this.damage = damage;
        this.cooldown = cooldown;
    }

    public SpellComponent(Entity self) {
        this.self = self;
        this.damage = currentSpell.getDamage();
        this.cooldown = currentSpell.getCooldown();
        this.manaCost = currentSpell.getManaCost();
        this.healing = currentSpell.getHealing();
    }

    @Override
    public int getBit() {
        return bit;
    }

    public SpellType getCurrentSpell() {
        return currentSpell;
    }

    @Override
    public void process(MessageChannel channel) {

    }

    public String cast() {
        now = (Sys.getTime() * 1000) / Sys.getTimerResolution();
        if (now - lastTime >= 1000 * cooldown) {
            lastTime = now;
            self.broadcast("requestMP");
            if (lastMana >= manaCost) {
                if (manaCost > 0) {
                    self.broadcast("drain " + manaCost);
                }
                if (healing > 0) {
                    self.broadcast("heal " + healing);
                }
                self.broadcast("animate " + currentSpell.getName());
                return "damage " + damage;
            } else {
                System.out.println("Need " + (int) (manaCost - lastMana) + " more mana for " + currentSpell.getName());
            }

        }
        return null;
    }

    @Override
    public void receive(String command) {
        String str = command;
        String[] list = null;
        if (str.matches("next spell")) {
            currentSpell = SpellType.values()[(currentSpell.ordinal() + 1) % (SpellType.values().length - 1)];
            System.out.println(currentSpell.toString());
            damage = currentSpell.getDamage();
            cooldown = currentSpell.getCooldown();
            manaCost = currentSpell.getManaCost();
            healing = currentSpell.getHealing();
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
