package components;

import others.Consts;
import others.MessageChannel;

public class LevelComponent implements Component {
    public int bit = Consts.LEVEL;

    @Override
    public void process(MessageChannel channel) {
        // TODO Auto-generated method stub

    }

    @Override
    public void receive(String command) {
        // TODO Auto-generated method stub

    }

    @Override
    public void update() {
        // TODO Auto-generated method stub

    }

    @Override
    public int getBit() {
        return bit;
    }
}
