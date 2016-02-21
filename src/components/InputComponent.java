package components;

import org.newdawn.slick.Input;

import others.Consts;
import others.Entity;
import others.MessageChannel;

public class InputComponent implements Component {
    public static int bit = 4;

    @Override
    public int getBit() {
        // TODO Auto-generated method stub
        return bit;
    }

    public void handleKey(Entity sender, String keyCode) {
        String cmd = null;
        switch (keyCode) {
        case "KEY_UP":
            cmd = "move 0 -10 0 1";
            break;
        case "KEY_DOWN":
            cmd = "move 0 10 0 1";
            break;
        case "KEY_LEFT":
            cmd = "move -10 0 0 1";
            break;
        case "KEY_RIGHT":
            cmd = "move 10 0 0 1";
            break;
        case "KEY_I":
            cmd = "drawIT";
            break;
        default:
            System.out.println(keyCode);
            return;
        }

        sender.process(new MessageChannel(sender, cmd));
    }

    @Override
    public void process(MessageChannel channel) {
        if (channel.getSender() == null) {
            return;
        }
        String[] list = null;
        String str = channel.getCommand();
        if (str.matches("handleKey [a-zA-Z0-9_]+")) {
            str = str.substring(10);
            list = str.split(" ");
            handleKey(channel.getSender(), list[0]);
        }
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub

    }

}
