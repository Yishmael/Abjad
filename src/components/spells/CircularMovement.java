package components.spells;

import components.Component;
import components.GuideComponent;
import components.units.TransformComponent;
import others.Consts;
import others.Entity;
import others.MainGame;
import others.MessageChannel;

public class CircularMovement implements Component, GuideComponent {
    private long id = Consts.CIRCULARMOVEMENT;
    private Entity self;
    private float speed, radius, cycles, cyclesRemaining, angle;

    public CircularMovement(Entity self, float speed, float cycles, float radius) {
        this.self = self;
        this.speed = speed;
        this.cycles = cycles;
        cyclesRemaining = cycles;
        this.radius = radius;
    }

    public void move(float dx, float dy) {
        ((TransformComponent) self.getComponent(Consts.TRANSFORM)).move(
                MainGame.dt / 1000f * dx * speed * Consts.TILE_SIZE,
                MainGame.dt / 1000f * dy * speed * Consts.TILE_SIZE);
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
        angle += 0.01f;
        angle %= Math.PI * 2;
        float dx = (float) (Math.cos(angle));
        float dy = (float) (Math.sin(angle));

        move(dx, dy);

        if (cyclesRemaining <= 0) {
            self.broadcast("finished");
        }
    }

    // temp
    public void draw() {
    }

    @Override
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setCycles(int cycles) {
        this.cycles = cycles;
    }

    @Override
    public long getID() {
        return id;
    }
}
