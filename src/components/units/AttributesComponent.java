package components.units;

import components.Component;
import others.Consts;
import others.Entity;
import others.MessageChannel;

public class AttributesComponent implements Component {
    private long id = Consts.ATTRIBUTES;
    private int strength, agility, intelligence;
    private int available;
    private Entity self;

    public AttributesComponent(Entity self, int strength, int agility, int intelligence) {
        this.self = self;
        this.strength = strength;
        this.agility = agility;
        this.intelligence = intelligence;
        available = 1;
    }

    @Override
    public void process(MessageChannel channel) {
    }

    @Override
    public void receive(String command) {
        String str = command;

        if (str.matches("lvlup")) {
            available++;
            strength++;
            agility++;
            intelligence++;
            self.broadcast("STR 1");
            self.broadcast("AGI 1");
            self.broadcast("INT 1");
        } else if (available > 0) {
            if (str.matches("STR[+][+]")) {
                if (strength < 100) {
                    strength++;
                    available--;
                    self.broadcast("STR 1");
                }
            } else if (str.matches("AGI[+][+]")) {
                if (agility < 100) {
                    agility++;
                    available--;
                    self.broadcast("AGI 1");
                }
            } else if (str.matches("INT[+][+]")) {
                if (intelligence < 100) {
                    intelligence++;
                    available--;
                    self.broadcast("INT 1");
                }
            }
        }
    }

    public int getAvailablePoints() {
        return available;
    }

    @Override
    public void update() {
    }

    public float getStrength() {
        return strength;
    }

    public float getAgility() {
        return agility;
    }

    public float getIntelligence() {
        return intelligence;
    }

    @Override
    public long getID() {
        return id;
    }
}
