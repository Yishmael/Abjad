package components.units;

import java.util.ArrayList;

import org.lwjgl.Sys;

import components.Component;
import others.Consts;
import others.Entity;
import others.MessageChannel;

public class AttackComponent implements Component {
    private long id = Consts.ATTACK;
    private float cooldown = 9999, damage;
    private float damageMul = 1, cooldownMul = 1, critChance, lifesteal;
    private int rangeAdder;
    private Entity self;
    private long lastTime;
    private boolean canAttack = true;
    private ArrayList<String> modifiers = new ArrayList<String>();

    // default attack
    public AttackComponent(Entity self) {
        this.self = self;
        this.damage = 1;
        this.cooldown = 1;
        this.rangeAdder = 0;
    }

    public AttackComponent(Entity self, float damage, float cooldown) {
        this.self = self;
        this.damage = damage;
        this.cooldown = cooldown;
    }

    public boolean isReady() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution() - lastTime >= 1000 * getCooldown();
    }

    public boolean canAttack() {
        return canAttack;
    }

    public ArrayList<String> attack() {
        ArrayList<String> list = new ArrayList<String>();

        self.broadcast("animate Attack");
        float critMul = Math.random() <= critChance ? 2 : 1;

        float diced = (float) (getDamage() * critMul + getDamage() * critMul * 0.15 * (Math.random() - Math.random()));
        if (critMul == 2) {
            System.out.println("Critical hit (" + (int) diced + " damage)");
        }
        lastTime = (Sys.getTime() * 1000) / Sys.getTimerResolution();

        // always deal physical damage before modifiers
        list.add(0, "physdmg " + diced);
        list.addAll(modifiers);

        for (String string: modifiers) {
//            System.out.println(string);
        }

        return list;
    }

    public void defend() {
        // self.broadcast("damage " + (int) (damage - defense * 0.2));
    }

    public float getRangeAdder() {
        return rangeAdder;
    }

    @Override
    public void process(MessageChannel channel) {

    }

    @Override
    public void receive(String command) {
        String str = command;
        String[] list = null;
        if (str.matches("equippedW [0-9]+[.]?[0-9]* [0-9]+[.]?[0-9]* [0-9]+")) {
            str = str.substring(10);
            list = str.split(" ");
            damage = Float.parseFloat(list[0]);
            cooldown = Float.parseFloat(list[1]);
            rangeAdder = Integer.parseInt(list[2]);
            System.out.println((int) damage + "/" + cooldown + "/" + rangeAdder);
        } else if (str.matches("unequippedW")) {
            damage = 1;
            cooldown = 1;
            rangeAdder = 0;
        } else if (str.matches("died")) {
            canAttack = false;
        } else if (str.matches("ressed")) {
            canAttack = true;
        } else if (str.matches("AS [-]?[0-9]+[.]?[0-9]*[%]")) {
            System.out.println(str);
            str = str.substring(3, str.indexOf('%'));
            float temp = Float.parseFloat(str);
            cooldownMul *= 1 - temp / 100f;
        } else if (str.matches("AS [-]?[0-9]+[.]?[0-9]*")) {
            System.out.println(str);
            str = str.substring(3);
            float temp = Float.parseFloat(str);
            cooldownMul *= 1 - temp / 100f;
        } else if (str.matches("DMG [-]?[0-9]+[.]?[0-9]*[%]")) {
            System.out.println(str);
            str = str.substring(4, str.indexOf('%'));
            float temp = Float.parseFloat(str);
            damageMul *= 1 + temp / 100f;
        } else if (str.matches("DMG [-]?[0-9]+[.]?[0-9]*")) {
            System.out.println(str);
            str = str.substring(4);
            float temp = Float.parseFloat(str);
            damage += temp;
        } else if (str.matches("CRT [-]?[0-9]+[.]?[0-9]*[%]")) {
            System.out.println(str);
            str = str.substring(4, str.indexOf('%'));
            float temp = Float.parseFloat(str);
            critChance += temp / 100f;
        } else if (str.matches("LS [-]?[0-9]+[.]?[0-9]*[%]")) {
            System.out.println(str);
            str = str.substring(3, str.indexOf('%'));
            float temp = Float.parseFloat(str);
            lifesteal += temp / 100f;
        } else if (str.matches("CD [-]?[0-9]+[.]?[0-9]*[%]")) {
            System.out.println(str);
            str = str.substring(3, str.indexOf('%'));
            float temp = Float.parseFloat(str);
            cooldownMul = 1 - temp / 100;
        } else if (str.matches("attackMod [a-z]+ [-]?[0-9]+[.]?[0-9]*")) {
            System.out.println(str);
            str = str.substring(10);
            list = str.split(" ");
            String damageType = list[0];
            float damageBonus = Float.parseFloat(list[1]);
            // temp
            if (damageBonus < 0) {
                modifiers.remove(damageType + "dmg " + -damageBonus);
            } else {
                modifiers.add(damageType + "dmg " + damageBonus);
            }
        }
    }

    @Override
    public void update() {
        // System.out.println(delta);
        // System.out.println(LocalTime.now() + "updating attackcomp");
    }

    public float getCooldown() {
        return cooldown * cooldownMul;
    }

    public float getCooldownMillis() {
        return cooldown * cooldownMul * 1000;
    }

    public float getDamage() {
        return damage * damageMul;
    }

    public float getLifesteal() {
        return lifesteal;
    }

    @Override
    public long getID() {
        return id;
    }

    @Override
    public void draw() {
        // TODO Auto-generated method stub

    }

}
