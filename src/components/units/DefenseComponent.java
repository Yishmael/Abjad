package components.units;

import components.Component;
import others.Consts;
import others.Entity;
import others.MessageChannel;

public class DefenseComponent implements Component {
    private long id = Consts.DEFENSE;
    private Entity self;

    private float defense, defenseMul = 1;

    public DefenseComponent(Entity self, float defense) {
        this.self = self;
        this.defense = defense;
    }

    @Override
    public void process(MessageChannel channel) {
        if (channel != null) {
            receive(channel.getCommand());
        }
    }

    @Override
    public void receive(String command) {
        String str = command;
        if (str.matches("DEF [-]?[0-9]+[.]?[0-9]*")) {
            str = str.substring(4);
            float temp = Float.parseFloat(str);
            defense += temp;
            System.out.println("Def: " + defense);
        } else if (str.matches("DEF [-]?[0-9]+[.]?[0-9]*[%]")) {
            str = str.substring(4, str.indexOf('%'));
            float temp = Float.parseFloat(str);
            defenseMul *= 1 + temp / 100f;
            System.out.println("Def bonus: " + (1 - defenseMul) + "%");
        } else if (str.matches("physdmg [0-9]+[.]?[0-9]*")) {
            str = str.substring(8);
            float temp = Float.parseFloat(str);
            temp *= 1 - getDefense() / (getDefense() + 2000);
            self.broadcast("HPdelta " + -temp);
        }
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub

    }

    public float getDefense() {
        return defense * defenseMul;
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
