package components;

import others.MessageChannel;

public class DefendComponent implements Component {
    public static int bit = 2;

    public void defend(String name, int def) {

    }

    @Override
    public int getBit() {
        return bit;
    }

    @Override
    public void process(MessageChannel channel) {
        String str = channel.getCommand();
        if (str.length() >= 8) {
            if (str.substring(0, 6).equals("defend")) {
                defend(channel.getSender().getName(), Integer.parseInt(str.substring(7)));
            }
        }
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub

    }

}
