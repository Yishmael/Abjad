package components;

import others.MessageChannel;

public class AttackComponent implements Component {
    public static int bit = 1;
    private float speed;
    private int damage;

    public AttackComponent(int damage, float speed) {
        // TODO Auto-generated constructor stub
        this.damage = damage;
        this.speed = speed;
    }

    @Override
    public int getBit() {
        return bit;
    }

    public int getDamage() {
        return damage;
    }

    public float getSpeed() {
        return speed;
    }

    @Override
    public void process(MessageChannel channel) {
        String str = channel.getCommand();
        if (str.length() >= 8) {
            if (str.substring(0, 6).equals("strike")) {
                strike(channel.getSender().getName(), Integer.parseInt(str.substring(7)));
            }
        }
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void strike(String name, int damage) {
        System.out.println("Dealt " + damage + " damage" + " to " + name);
    }

    @Override
    public void update() {
        // System.out.println(delta);
        // System.out.println(LocalTime.now() + "updating attackcomp");

    }
}
