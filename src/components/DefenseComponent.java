package components;

import java.time.LocalTime;

import others.Consts;
import others.Entity;
import others.MessageChannel;

public class DefenseComponent implements Component {
    private int id = Consts.DEFENSE;
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
            return;
        }
        if (str.matches("DEF [-]?[0-9]+[.]?[0-9]*[%]")) {
            str = str.substring(4, str.indexOf('%'));
            float temp = Float.parseFloat(str);
            defenseMul *= 1 + temp / 100f;
            return;
        }
        if (str.matches("physdmg [0-9]+[.]?[0-9]*")) {
            str = str.substring(8);
            float temp = Float.parseFloat(str);
            temp *= 1 - getDefense() / (getDefense() + 2000);
            self.broadcast("HPdelta " + -temp);
            return;
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
    public int getID() {
        return id;
    }
}
