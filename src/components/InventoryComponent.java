package components;

import others.MessageChannel;

public class InventoryComponent implements Component {
    public static int bit = 5;

    @Override
    public int getBit() {
        return bit;
    }

    private void inv(String name, int parseInt) {
        // TODO Auto-generated method stub

    }

    @Override
    public void process(MessageChannel channel) {
        String str = channel.getCommand();
        if (str.length() >= 8) {
            if (str.substring(0, 6).equals("inventory")) {
                inv(channel.getSender().getName(), Integer.parseInt(str.substring(7)));
            }
        }
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub

    }

}
