package components.units;

import components.Component;
import components.MovementComponent;
import others.Consts;
import others.Entity;
import others.MessageChannel;

public class PlayerMovementCompenent implements Component, MovementComponent {
    private long id = Consts.PLAYERMOVEMENT;
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
        } else if (str.matches("ressed")) {
            canMove = true;
        } else if (str.matches("MS [-]?[0-9]+[.]?[0-9]*[%]")) {
            str = str.substring(3, str.indexOf('%'));
            float temp = Float.parseFloat(str);
            speelMul += temp / 100f;
            System.out.println("MS: " + getSpeed());
        } else if (str.matches("MS [-]?[0-9]+[.]?[0-9]*")) {
            str = str.substring(3);
            float temp = Float.parseFloat(str);
            speelMul += temp / 100f;
            System.out.println("MS: " + getSpeed());
        }
    }

    @Override
    public float getSpeed() {
        return speed * speelMul;
    }

    @Override
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    @Override
    public boolean canMove() {
        return canMove;
    }

    @Override
    public void update() {
    }

    @Override
    public void move(float dx, float dy) {
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
