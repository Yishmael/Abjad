package components;

import org.lwjgl.Sys;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import others.Consts;
import others.Entity;
import others.MessageChannel;

public class AttackComponent implements Component {
    private int id = Consts.ATTACK;
    private float cooldown = 9999, damage;
    private float damageMul = 1, cooldownMul = 1, critChance, cleaveRadius, lifesteal;
    private int rangeAdder;
    private Entity self;
    private long lastTime = 0;
    private long now = 0;
    private boolean canAttack = true;
    private Sound sound;

    public AttackComponent(Entity self, float damage, float cooldown) {
        this.self = self;
        this.damage = damage;
        this.cooldown = cooldown;
        try {
            sound = new Sound("sounds/032.ogg");
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public AttackComponent(Entity self) {
        this.self = self;
        try {
            sound = new Sound("sounds/032.ogg");
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public boolean isReady() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution() - lastTime >= 1000 * getCooldown();
    }

    public String attack() {
        if (canAttack) {
            now = (Sys.getTime() * 1000) / Sys.getTimerResolution();
            if (now - lastTime >= 1000 * getCooldown()) {
                lastTime = now;
                self.broadcast("animate Attack");
                float critMul = Math.random() <= critChance ? 2 : 1;

                float diced = (float) (getDamage() * critMul
                        + getDamage() * critMul * 0.15 * (Math.random() - Math.random()));
                if (critMul == 2) {
                    System.out.println("Critical hit (" + (int) diced + " damage)");
                }
                sound.play();
                return "physdmg " + diced;
            }
        }
        // self.broadcast("damage " + damage);
        return null;
    }

    public void defend() {
        // self.broadcast("damage " + (int) (damage - defense * 0.2));
    }

    public float getCleaveRadius() {
        return cleaveRadius;
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
        if (canAttack) {
            if (str.matches("attack")) {
                attack();
                return;
            }
        }
        if (str.matches("equippedW [0-9]+[.]?[0-9]* [0-9]+[.]?[0-9]* [0-9]+")) {
            str = str.substring(10);
            list = str.split(" ");
            damage = Float.parseFloat(list[0]);
            cooldown = Float.parseFloat(list[1]);
            rangeAdder = Integer.parseInt(list[2]);
            System.out.println((int) damage + "/" + cooldown + "/" + rangeAdder);
            return;
        }
        if (str.matches("died")) {
            canAttack = false;
            return;
        }
        if (str.matches("ressed")) {
            canAttack = true;
            return;
        }
        if (str.matches("AS [-]?[0-9]+[.]?[0-9]*[%]")) {
            System.out.println(str);
            str = str.substring(3, str.indexOf('%'));
            float temp = Float.parseFloat(str);
            cooldownMul *= 1 - temp / 100f;
            return;
        }
        if (str.matches("DMG [-]?[0-9]+[.]?[0-9]*[%]")) {
            str = str.substring(4, str.indexOf('%'));
            float temp = Float.parseFloat(str);
            damageMul *= 1 + temp / 100f;
            return;
        }
        if (str.matches("DMG [-]?[0-9]+[.]?[0-9]*")) {
            str = str.substring(4);
            float temp = Float.parseFloat(str);
            damage += temp;
            return;
        }
        if (str.matches("CRT [-]?[0-9]+[.]?[0-9]*[%]")) {
            str = str.substring(4, str.indexOf('%'));
            float temp = Float.parseFloat(str);
            critChance += temp / 100f;
            return;
        }
        if (str.matches("LS [-]?[0-9]+[.]?[0-9]*[%]")) {
            str = str.substring(3, str.indexOf('%'));
            float temp = Float.parseFloat(str);
            lifesteal += temp / 100f;
            return;
        }
        if (str.matches("CLV [-]?[0-9]+[.]?[0-9]*[%]")) {
            str = str.substring(4, str.indexOf('%'));
            float temp = Float.parseFloat(str);
            cleaveRadius += temp;
            return;
        }
        if (str.matches("CD [-]?[0-9]+[.]?[0-9]*[%]")) {
            str = str.substring(3, str.indexOf('%'));
            float temp = Float.parseFloat(str);
            cooldownMul = 1 - temp / 100;
            return;
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
    public int getID() {
        return id;
    }

}
