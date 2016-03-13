package components;

import java.util.ArrayList;

import org.lwjgl.Sys;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import enums.SpellType;
import others.Consts;
import others.Entity;
import others.MessageChannel;

public class SpellComponent implements Component {
    private int id = Consts.SPELL;
    private float manaCost, damage, healing, cooldown, lastMana;
    private boolean canCast = true;
    private float damageMul = 1, healingMul = 1;
    private Entity self;
    private long lastTime, now;
    private SpellType currentSpell = SpellType.Null;
    private Sound sounds[] = new Sound[2];
    private ArrayList<SpellType> spells = new ArrayList<SpellType>();

    public SpellComponent(Entity self, SpellType[] spells) throws SlickException {
        this.self = self;
        if (spells != null && spells.length > 0) {
            for (SpellType spell: spells) {
                if (spell != SpellType.Null) {
                    if (!hasSpell(spell)) {
                        this.spells.add(spell);
                    }
                }
            }
            currentSpell = this.spells.get(0);
            damage = currentSpell.getDamage() * damageMul;
            healing = currentSpell.getHealing() * healingMul;
            cooldown = currentSpell.getCooldown();
            manaCost = currentSpell.getManaCost();
            sounds[0] = new Sound(currentSpell.getSoundPath());
            sounds[1] = new Sound(currentSpell.getDeathSoundPath());
        }
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

    public boolean hasSpell(SpellType spell) {
        for (SpellType s: spells) {
            if (spell == s) {
                return true;
            }
        }
        return false;
    }

    public boolean isReady() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution() - lastTime >= 1000 * cooldown;
    }

    public boolean canCast() {
        return canCast;
    }

    public String cast() {
        if (canCast) {
            now = (Sys.getTime() * 1000) / Sys.getTimerResolution();
            if (now - lastTime >= 1000 * cooldown) {
                lastTime = now;
                self.broadcast("requestMP");
                if (lastMana >= manaCost) {
                    if (manaCost > 0) {
                        self.broadcast("drain " + manaCost);
                    }
                    if (healing > 0) {
                        // self.broadcast("heal " + healing);
                    }
                    self.broadcast("animate " + currentSpell.getName());
                    return "damage " + damage;
                } else {
                    System.out.println(
                            "Need " + (int) (manaCost - lastMana) + " more mana for " + currentSpell.getName());
                }

            }
        }
        return null;
    }

    @Override
    public void receive(String command) {
        String str = command;
        String[] list = null;
        if (str.matches("next spell")) {
            if (spells.size() > 0) {
                currentSpell = spells.get((spells.indexOf(currentSpell) + 1) % spells.size());
                System.out.println(currentSpell.toString());
                damage = currentSpell.getDamage() * damageMul;
                healing = currentSpell.getHealing() * healingMul;
                cooldown = currentSpell.getCooldown();
                manaCost = currentSpell.getManaCost();
            }
            return;
        }
        if (str.matches("updateMP [0-9]+[.]?[0-9]* [0-9]+[.]?[0-9]*")) {
            str = str.substring(9);
            list = str.split(" ");
            lastMana = Float.parseFloat(list[0]);
            return;
        }
        if (str.matches("SP [-]?[0-9]+[.]?[0-9]*[%]")) {
            str = str.substring(3, str.indexOf('%'));
            float temp = Float.parseFloat(str);
            // make it give bonus based on base value?
            damageMul *= 1 + temp / 100f;
            healingMul *= 1 + temp / 100f;
        }
        if (str.matches("died")) {
            canCast = false;
            return;
        }
        if (str.matches("ressed")) {
            canCast = true;
            return;
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
