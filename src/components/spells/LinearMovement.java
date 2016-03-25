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
        ((TransformComponent) self.getComponent(Consts.TRANSFORM)).move(
                MainGame.dt / 1000f * dx * speed * Consts.TILE_SIZE,
                MainGame.dt / 1000f * dy * speed * Consts.TILE_SIZE);

        float tempX = MainGame.dt / 1000f * dx * speed * Consts.TILE_SIZE;
        float tempY = MainGame.dt / 1000f * dy * speed * Consts.TILE_SIZE;
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
}
