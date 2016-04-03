package components.units;

import java.util.ArrayList;

import org.lwjgl.Sys;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import components.Component;
import others.Consts;
import others.Entity;
import others.MessageChannel;
import others.Spell;

public class CastComponent implements Component {
    private long id = Consts.SPELL;
    private Entity self;

    private boolean canCast = true;
    private float lastMana;
    private float damageMul = 1, healingMul = 1;
    private long lastTime;
    private Spell currentSpell = null;
    private ArrayList<Spell> spells = new ArrayList<Spell>();

    public CastComponent(Entity self, Spell[] spells) throws SlickException {
        this.self = self;
        if (spells != null && spells.length > 0) {
            for (Spell spell: spells) {
                if (!spell.getName().equalsIgnoreCase("Null")) {
                    if (!hasSpell(spell)) {
                        this.spells.add(spell);
                    }
                }
            }
            currentSpell = Spell.getSpellData(this.spells.get(0).getName());
        }
    }

    @Override
    public long getID() {
        return id;
    }

    public Spell getCurrentSpell() {
        return currentSpell;
    }

    @Override
    public void process(MessageChannel channel) {

    }

    public boolean hasSpell(Spell spell) {
        for (Spell s: spells) {
            if (spell == s) {
                return true;
            }
        }
        return false;
    }

    public boolean isReady() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution() - lastTime >= 1000 * currentSpell.getCooldown();
    }

    public boolean canCast() {
        return canCast;
    }

    public boolean hasMana() {
        self.broadcast("requestMP");
        return lastMana >= currentSpell.getManaCost();
    }

    public void cast() throws SlickException {
        if (hasMana()) {
            self.broadcast("MPdelta " + -currentSpell.getManaCost());
            self.broadcast("animate " + currentSpell.getName());
            lastTime = (Sys.getTime() * 1000) / Sys.getTimerResolution();
            new Sound(currentSpell.getCreationSoundPath()).play();
        } else {
            System.out.println("Need " + (int) (currentSpell.getManaCost() - lastMana) + " more mana for "
                    + currentSpell.getName());
        }
    }

    public void nextSpell() {
        currentSpell = spells.get((spells.indexOf(currentSpell) + 1) % spells.size());
    }

    public void prevSpell() {
        currentSpell = spells.get(((spells.indexOf(currentSpell) - 1) + spells.size()) % spells.size());
    }

    @Override
    public void receive(String command) {
        String str = command;
        String[] list = null;
        if (str.matches("(next|prev){1} spell")) {
            if (spells.size() > 1) {
                if (str.equalsIgnoreCase("next spell")) {
                    currentSpell = spells.get((spells.indexOf(currentSpell) + 1) % spells.size());
                } else {
                    currentSpell = spells.get(((spells.indexOf(currentSpell) - 1) + spells.size()) % spells.size());
                }
                // System.out.println(currentSpell.getName());
            }
        } else if (str.matches("updateMP [0-9]+[.]?[0-9]* [0-9]+[.]?[0-9]*")) {
            str = str.substring(9);
            list = str.split(" ");
            lastMana = Float.parseFloat(list[0]);
        } else if (str.matches("SP [-]?[0-9]+[.]?[0-9]*[%]")) {
            str = str.substring(3, str.indexOf('%'));
            float temp = Float.parseFloat(str);
            // make it give bonus based on base value?
            damageMul *= 1 + temp / 100f;
            healingMul *= 1 + temp / 100f;
            System.out.println("Spell bonus: " + (1 - damageMul) + "%");
        } else if (str.matches("died")) {
            canCast = false;
        } else if (str.matches("ressed")) {
            canCast = true;
        } else if (str.matches("learn [a-zA-Z]+")) {
            str = str.substring(6);
            if (!str.equalsIgnoreCase("Null")) {
                Spell newSpell = Spell.getSpellData(str);
                if (newSpell != null) {
                    if (!hasSpell(newSpell)) {
                        spells.add(newSpell);
                        System.out.println("Learned " + newSpell.getName());
                    }
                }
            }
        }
    }

    @Override
    public void update() {

    }

    public float getManaCost() {
        return currentSpell.getManaCost();
    }

    public float getDamage() {
        return currentSpell.getDamage() * damageMul;
    }

    public String getDamageType() {
        return currentSpell.getDamageType();
    }

    public float getHealing() {
        return currentSpell.getHealing() * healingMul;
    }

    @Override
    public void draw() {
        // TODO Auto-generated method stub

    }

}
