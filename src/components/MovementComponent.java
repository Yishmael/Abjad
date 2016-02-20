package components;

import others.MessageChannel;

public class MovementComponent implements Component {
    public static int bit = 6;
    private float speed;

    public MovementComponent(float speed) {
        this.speed = speed;
    }

    @Override
    public int getBit() {
        return bit;
    }

    public float getSpeed() {
        return speed;
    }

    public boolean isRunning() {
        return speed >= 5;
    }

    @Override
    public void process(MessageChannel channel) {

    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    @Override
    public void update() {

    }
}
