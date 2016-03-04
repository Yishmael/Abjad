package components;

import org.lwjgl.Sys;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import others.Consts;
import others.Entity;
import others.ItemType;
import others.MessageChannel;

public class CombatComponent implements Component {
    private int bit = Consts.COMBAT;
    private float cooldown, damage, defense;
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
            // TODO Auto-generated catch block
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
            // TODO Auto-generated catch block
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
            if (now - lastTime >= 1000 * cooldown) {
                lastTime = now;
                self.broadcast("animate Attack");
                float diced = (float) (damage + damage * 0.15 * (Math.random() - Math.random()));
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

    @Override
    public int getBit() {
        return bit;
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

            damage = temp.getDamage();
            defense = temp.getDefense();
            cooldown = temp.getCooldown();
            System.out.println(temp.toString());
            return;
        }

        if (str.matches("died")) {
            canAttack = false;
        }
    }

    @Override
    public void update() {
        // System.out.println(delta);
        // System.out.println(LocalTime.now() + "updating attackcomp");

    }
}
