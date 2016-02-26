package components;

import org.lwjgl.Sys;

import others.Consts;
import others.Entity;
import others.MessageChannel;

public class CombatComponent implements Component {
    private int bit = Consts.COMBAT;
    private float speed, damage, defense;
    private Entity self;
    private long lastTime = 0;
    private long now = 0;

    public CombatComponent(Entity self, float damage, float defense, float speed) {
        this.self = self;
        this.damage = damage;
        this.defense = defense;
        this.speed = speed;
    }

    public String attack() {
        now = (Sys.getTime() * 1000) / Sys.getTimerResolution();
        if (now - lastTime >= 1000 * speed) {
            lastTime = now;
            self.broadcast("attacked");
            return "damage " + damage;
        }
        // self.broadcast("damage " + damage);
        // System.out.println("Attacked with " + damage + " damage!");
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
        if (str.matches("attack")) {
            attack();
            return;
        }
        if (str.matches("defend")) {
            defend();
            return;
        }
    }

    @Override
    public void update() {
        // System.out.println(delta);
        // System.out.println(LocalTime.now() + "updating attackcomp");

    }
}
