package components;

import others.Consts;
import others.Entity;
import others.MessageChannel;

public class PlayerMovementCompenent implements MovementComponent {
    private int id = Consts.PLAYERMOVEMENT;
    private float speed, speelMul = 1;
    private Entity self;
    private boolean canMove = true;

    public PlayerMovementCompenent(Entity self, float speed) {
        this.self = self;
        this.speed = speed;
    }

    @Override
    public void process(MessageChannel channel) {
        if (channel.getSender() == null) {
            return;
        }
    }

    @Override
    public void receive(String command) {
        String str = command;
        if (str.matches("died")) {
            canMove = false;
            return;
        }
        if (str.matches("ressed")) {
            canMove = true;
            return;
        }
        if (str.matches("MS [-]?[0-9]+[.]?[0-9]*[%]")) {
            str = str.substring(3, str.indexOf('%'));
            float temp = Float.parseFloat(str);
            speelMul += temp / 100f;
            return;
        }
    }

    public float getSpeed() {
        return speed * speelMul;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public boolean canMove() {
        return canMove;
    }

    public void update() {
    }

    public void move(float dx, float dy) {
    }

    public int getID() {
        return id;
    }


}