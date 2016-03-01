package components;

import others.Consts;
import others.Entity;
import others.MessageChannel;

public class AttributesComponent implements Component {
    private int bit = Consts.ATTRIBUTES;
    private float strength, agility, intelligence;
    private int available;
    private Entity self;

    public AttributesComponent(Entity self, float strength, float agility, float intelligence) {
        this.self = self;
        this.strength = strength;
        this.agility = agility;
        this.intelligence = intelligence;
        available = 1;
    }

    @Override
    public void process(MessageChannel channel) {
        // TODO Auto-generated method stub

    }

    @Override
    public void receive(String command) {
        String str = command;

        if (str.matches("lvlup")) {
            available++;
            return;
        }
        if (available > 0) {
            if (str.matches("STR[+][+]")) {
                if (strength < 10) {
                    strength++;
                    available--;
                    update();
                }
                return;
            }
            if (str.matches("AGI[+][+]")) {
                if (agility < 10) {
                    agility++;
                    available--;
                    update();
                }
                return;
            }
            if (str.matches("INT[+][+]")) {
                if (intelligence < 10) {
                    intelligence++;
                    available--;
                    update();
                }
                return;
            }
        }

    }

    @Override
    public void update() {
        self.broadcast("STATS " + strength + " " + agility + " " + intelligence);
    }

    @Override
    public int getBit() {
        return bit;
    }
}
