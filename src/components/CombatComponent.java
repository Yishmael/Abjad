package components;

import org.lwjgl.Sys;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import enums.ItemType;
import others.Consts;
import others.Entity;
import others.MessageChannel;

public class CombatComponent implements Component {
    private int id = Consts.COMBAT;
    private float cooldown, damage, defense;
    private float damageMul = 1, cooldownMul = 1, critChance = 0, cleaveRadius = 0;
    private float lifesteal = 0;
    private Entity self;
    private long lastTime = 0;
    private long now = 0;
    private boolean canAttack = true;
    private Sound sound;

    public CombatComponent(Entity self, float damage, float defense, float cooldown) {
        this.self = self;
        this.damage = damage;
        this.defense = defense;
        this.cooldown = cooldown;
        try {
            sound = new Sound("sounds/032.ogg");
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public CombatComponent(Entity self, float damage, float cooldown) {
        this.self = self;
        this.damage = damage;
        this.defense = 0;
        this.cooldown = cooldown;
        try {
            sound = new Sound("sounds/032.ogg");
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public CombatComponent(Entity self) {
        this.self = self;
        receive("equipped 0");
        try {
            sound = new Sound("sounds/032.ogg");
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public CombatComponent(Entity self, float defense) {
        this.self = self;
        this.damage = 0;
        this.defense = defense;
        this.cooldown = 0;
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
                    System.out.println("Critical hit!");
                }
                // System.out.println("Attacked with " + (int)diced + "
                // damage!");
                sound.play();
                return "damage " + diced;
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

    @Override
    public void process(MessageChannel channel) {

    }

    @Override
    public void receive(String command) {
        String str = command;
        if (canAttack) {
            if (str.matches("attack")) {
                attack();
                return;
            }
            if (str.matches("defend")) {
                defend();
                return;
            }
        }
        if (str.matches("equipped [0-9]+")) {
            str = str.substring(9);
            ItemType temp = ItemType.values()[Integer.parseInt(str)];

            damage = temp.getDamage() * damageMul;
            defense = temp.getDefense();
            cooldown = temp.getCooldown() * cooldownMul;
            System.out.println(temp.toString());
            return;
        }
        if (str.matches("died")) {
            canAttack = false;
        }
        if (str.matches("AS [-]?[0-9]+[.]?[0-9]*")) {
            str = str.substring(3);
            float temp = Float.parseFloat(str);
            cooldownMul *= 1 - temp / 100f;
        }
        if (str.matches("DMG [-]?[0-9]+[.]?[0-9]*")) {
            str = str.substring(4);
            float temp = Float.parseFloat(str);
            damageMul *= 1 + temp / 100f;
        }
        if (str.matches("CRT [-]?[0-9]+[.]?[0-9]*")) {
            str = str.substring(4);
            float temp = Float.parseFloat(str);
            critChance += temp / 100f;
        }
        if (str.matches("LS [-]?[0-9]+[.]?[0-9]*")) {
            str = str.substring(3);
            float temp = Float.parseFloat(str);
            lifesteal += temp / 100f;
        }
        if (str.matches("CLV [-]?[0-9]+[.]?[0-9]*")) {
            str = str.substring(4);
            float temp = Float.parseFloat(str);
            cleaveRadius += temp;
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
