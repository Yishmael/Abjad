package components;

import others.Consts;
import others.Entity;
import others.MainGame;
import others.MessageChannel;

public class SpellComponent implements Component {
    private int bit = Consts.SPELL;
    private float manaCost, damage, speed, lastMana = 0;
    private boolean alive;
    private Entity self;
    private float dt = 0;

    public SpellComponent(Entity self, float manaCost, float damage, float speed) {
        this.self = self;
        this.manaCost = manaCost;
        this.damage = damage;
        this.speed = speed;
    }

    public String fireball() {
        self.broadcast("requestMP");
        if (manaCost <= lastMana) {
            self.broadcast("drain " + manaCost);
            // self.broadcast("damage " + damage);
//            System.out.println("Casting successful!");
            return "damage " + damage;
        } else {
            System.out.println("Need " + (int) (manaCost - lastMana) + " more mana for Fireball!");
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

    public String nourish() {
        dt += MainGame.dt;
        if (dt >= 1000 * speed) {
            dt = 0;
            self.broadcast("requestMP");
            if (manaCost <= lastMana) {
                self.broadcast("drain " + manaCost);
                return "heal " + damage;
            } else {
                System.out.println("Need " + (int) (manaCost - lastMana) + " more mana for Nourish!");
            }
        }
        return null;
    }

    @Override
    public void process(MessageChannel channel) {

    }

    @Override
    public void receive(String command) {
        String str = command;
        String[] list = null;
        if (str.matches("cast fireball")) {
            fireball();
            return;
        }
        if (str.matches("cast heal")) {
            nourish();
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
