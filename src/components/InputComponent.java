package components;

import org.newdawn.slick.Input;

import others.Consts;
import others.Entity;
import others.MessageChannel;

public class InputComponent implements Component {
    private int id = Consts.INPUT;

    private Entity self;

    public InputComponent(Entity self) {
        this.self = self;
    }

    @Override
    public int getID() {
        return id;
    }

    public void handleKey(int keyCode) {
        String command = null;
        switch (keyCode) {
        case Input.KEY_UP:
        case Input.KEY_DOWN:
        case Input.KEY_LEFT:
        case Input.KEY_RIGHT:
            command = "animate Walk";
            break;
        case Input.KEY_I:
            command = "toggleInv";
            break;
        case Input.KEY_ESCAPE:
            command = "invOff";
            break;
        case Input.KEY_A:
            command = "attack";
            break;
        case Input.KEY_1:
            command = "next item";
            break;
        case Input.KEY_2:
            command = "next spell";
            break;
        default:
            System.out.println("Unknown command: " + "\"" + command + "\"");
            return;
        }
        self.broadcast(command);
    }

    @Override
    public void process(MessageChannel channel) {

    }

    @Override
    public void receive(String command) {
        String str = command;
        if (str.matches("KEY [0-9]+")) {
            str = str.substring(4);
            handleKey(Integer.parseInt(str));
            return;
        }
        // if (str.matches("listen")) {
        // update();
        // }
    }

    @Override
    public void update() {
        // System.out.println(Keyboard.getEventKey());
    }

}
