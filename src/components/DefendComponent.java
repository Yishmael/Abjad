package components;

import others.MessageChannel;

public class DefendComponent implements Component {
    public static int bit = 2;

    public void defend(String name, int def) {
        System.out.println("defended " + def + " by " + name);
    }

    @Override
    public int getBit() {
        return bit;
    }

    @Override
    public void process(MessageChannel channel) {
        if (channel.getSender() == null) {
            return;
        }
        String str = channel.getCommand();
        if (str.matches("defend")) {
            defend(channel.getSender().getName(), Integer.parseInt(str.substring(7)));
        }
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub

    }

}
