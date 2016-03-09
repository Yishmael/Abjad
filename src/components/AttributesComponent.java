package components;

import others.Consts;
import others.Entity;
import others.MessageChannel;

public class AttributesComponent implements Component {
    private int id = Consts.ATTRIBUTES;
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
            strength++;
            agility++;
            intelligence++;
            brdcst();
            return;
        }
        if (available > 0) {
            if (str.matches("STR[+][+]")) {
                if (strength < 100) {
                    strength++;
                    available--;
                    brdcst();
                }
                return;
            }
            if (str.matches("AGI[+][+]")) {
                if (agility < 100) {
                    agility++;
                    available--;
                    brdcst();
                }
                return;
            }
            if (str.matches("INT[+][+]")) {
                if (intelligence < 100) {
                    intelligence++;
                    available--;
                    brdcst();
                }
                return;
            }
        }
        if (str.matches("STR[+]5")) {
            if (strength < 100) {
                strength += 5;
                brdcst();
            }
            return;
        }
        if (str.matches("AGI[+]5")) {
            if (agility < 100) {
                agility += 5;
                brdcst();
            }
            return;
        }
        if (str.matches("INT[+]5")) {
            if (intelligence < 100) {
                intelligence += 5;
                brdcst();
            }
            return;
        }
    }

    private void brdcst() {
        self.broadcast("STATS " + strength + " " + agility + " " + intelligence);
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
    public int getID() {
        return id;
    }
}
