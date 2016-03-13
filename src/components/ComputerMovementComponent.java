package components;

import others.Consts;
import others.Entity;
import others.MainGame;
import others.MessageChannel;

public class ComputerMovementComponent implements MovementComponent {
    private int id = Consts.COMPUTERMOVEMENT;
    private float speed, speelMul = 1;
    private Entity self;
    private boolean canMove = true;

    public ComputerMovementComponent(Entity self, float speed) {
        this.self = self;
        this.speed = speed;
    }

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

    public int getID() {
        return id;
    }

}
