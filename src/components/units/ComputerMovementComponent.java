package components.units;

import components.Component;
import components.MovementComponent;
import others.Consts;
import others.Entity;
import others.MainGame;
import others.MessageChannel;

public class ComputerMovementComponent implements Component, MovementComponent {
    private long id = Consts.COMPUTERMOVEMENT;
    private float speed, speelMul = 1, aquisitionRange, minimumDistance;
    private Entity self;
    private boolean canMove = true;

    public ComputerMovementComponent(Entity self, float speed, float aquisitionRange, float minimumDistance) {
        this.self = self;
        this.speed = speed;
        this.aquisitionRange = aquisitionRange;
        this.minimumDistance = minimumDistance;
    }

    @Override
    public void move(float dx, float dy) {
        if (canMove) {
            ((TransformComponent) self.getComponent(Consts.TRANSFORM)).move(
                    MainGame.dt / 1000f * dx * speed * Consts.TILE_SIZE,
                    MainGame.dt / 1000f * dy * speed * Consts.TILE_SIZE);
            // ((SpriteComponent)self.getComponent(Consts.SPRITE)).animateWalk();
            // self.broadcast("move " + MainGame.dt / 1000f * x * getSpeed() *
            // Consts.TILE_SIZE + " "
            // + MainGame.dt / 1000f * y * getSpeed() * Consts.TILE_SIZE);
            // self.broadcast("animate Walk");
        }
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
        }
    }

    public float getAquisitionRange() {
        return aquisitionRange;
    }

    public float getMinimumDistance() {
        return minimumDistance;
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
    public long getID() {
        return id;
    }

    @Override
    public void draw() {
        // TODO Auto-generated method stub

    }

}
