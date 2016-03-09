package components;

import org.lwjgl.Sys;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import enums.SpellType;
import others.Consts;
import others.Entity;
import others.MessageChannel;

public class SpellComponent implements Component {
    private int id = Consts.SPELL;
    private float manaCost, damage, healing, cooldown, lastMana = 0;
    private float damageMul = 1, healingMul = 1;
    private Entity self;
    private long lastTime = 0;
    private long now = 0;
    private SpellType currentSpell = SpellType.values()[0];
    private Sound sounds[] = new Sound[2];

    public SpellComponent(Entity self, float manaCost, float damage, float cooldown) throws SlickException {
        this.self = self;
        this.manaCost = manaCost;
        this.damage = damage;
        this.cooldown = cooldown;
        sounds[0] = new Sound(currentSpell.getSoundPath());
        sounds[1] = new Sound(currentSpell.getDeathSoundPath());
    }

    public SpellComponent(Entity self) throws SlickException {
        this.self = self;
        this.damage = currentSpell.getDamage() * damageMul;
        this.healing = currentSpell.getHealing() * healingMul;
        this.cooldown = currentSpell.getCooldown();
        this.manaCost = currentSpell.getManaCost();
        sounds[0] = new Sound(currentSpell.getSoundPath());
        sounds[1] = new Sound(currentSpell.getDeathSoundPath());
    }

    @Override
    public int getID() {
        return id;
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
            damage = currentSpell.getDamage() * damageMul;
            healing = currentSpell.getHealing() * healingMul;
            cooldown = currentSpell.getCooldown();
            manaCost = currentSpell.getManaCost();
            return;
        }
        if (str.matches("updateMP [0-9]+[.]?[0-9]* [0-9]+[.]?[0-9]*")) {
            str = str.substring(9);
            list = str.split(" ");
            lastMana = Float.parseFloat(list[0]);
            return;
        }
        if (str.matches("SP [-]?[0-9]+[.]?[0-9]*")) {
            str = str.substring(3);
            float temp = Float.parseFloat(str);
            // make it give bonus based on base value?
            damageMul *= 1 + temp / 100f;
            healingMul *= 1 + temp / 100f;
        }
    }

    @Override
    public void update() {

    }

    public float getManaCost() {
        return manaCost;
    }

    public float getDamage() {
        return damage * damageMul;
    }

    public float getHealing() {
        return healing * healingMul;
    }

}
