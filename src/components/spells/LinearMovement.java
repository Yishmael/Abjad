package components.spells;

import components.Component;
import components.GuideComponent;
import components.units.TransformComponent;
import others.Consts;
import others.Entity;
import others.MainGame;
import others.MessageChannel;

public class LinearMovement implements Component, GuideComponent {
    private long id = Consts.LINEARMOVEMENT;
    private Entity self;
    private float speed, range, angle, distanceTravelled;

    public LinearMovement(Entity self, float speed, float range, float angle) {
        this.self = self;
        this.speed = speed;
        this.range = range;
        this.angle = angle;
    }

    public void move(float dx, float dy) {
        float tempX = MainGame.dt / 1000f * dx * speed * Consts.TILE_SIZE;
        float tempY = MainGame.dt / 1000f * dy * speed * Consts.TILE_SIZE;

        ((TransformComponent) self.getComponent(Consts.TRANSFORM)).move(tempX, tempY);

        distanceTravelled += (float) Math.sqrt(tempX * tempX + tempY * tempY);
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
        if (str.matches("angle [-]?[0-9]+[.]?[0-9]*")) {
            str = str.substring(6);
            angle += Float.parseFloat(str) * Math.PI / 180f;
            angle %= Math.PI * 2;
        }
    }

    public float getSpeed() {
        return speed;
    }

    public void update() {
        move((float) Math.cos(angle), (float) Math.sin(angle));

        if (distanceTravelled >= range) {
            self.broadcast("finished");
        }
    }

    @Override
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setRange(float range) {
        this.range = range;
    }

    public long getID() {
        return id;
    }

    @Override
    public void draw() {
        // TODO Auto-generated method stub

    }
}
