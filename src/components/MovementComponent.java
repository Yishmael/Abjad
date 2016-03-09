package components;

import others.Consts;
import others.Entity;
import others.MainGame;
import others.MessageChannel;

public class MovementComponent implements Component {
    private int id = Consts.MOVEMENT;
    private float speed, speelMul = 1;
    private Entity self;
    private boolean canMove = true;

    public MovementComponent(Entity self, float speed) {
        this.self = self;
        this.speed = speed;
    }

    public void move(float x, float y) {
        // ((TransformComponent)self.getComponent(Consts.TRANSFORM)).move(MainGame.dt
        // / 1000f * x * speed * Consts.TILE_SIZE, MainGame.dt / 1000f * y *
        // speed * Consts.TILE_SIZE);
        // ((SpriteComponent)self.getComponent(Consts.SPRITE)).animateWalk();
        self.broadcast("move " + MainGame.dt / 1000f * x * getSpeed() * Consts.TILE_SIZE + " "
                + MainGame.dt / 1000f * y * getSpeed() * Consts.TILE_SIZE);
        self.broadcast("animate Walk");
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
        if (canMove) {
            if (str.matches("move UP")) {
                move(0, -1);
            } else if (str.matches("move DOWN")) {
                move(0, 1);
            }
            if (str.matches("move LEFT")) {
                move(-1, 0);
            } else if (str.matches("move RIGHT")) {
                move(1, 0);
            }
        }
        if (str.matches("died")) {
            canMove = false;
            return;
        }
        if (str.matches("ressed")) {
            canMove = true;
            return;
        }
        if (str.matches("MS [-]?[0-9]+[.]?[0-9]*")) {
            str = str.substring(3);
            float temp = Float.parseFloat(str);
            speelMul += temp / 100f;
            return;
        }
    }

    public float getSpeed() {
        return speed * speelMul;
    }

    @Override
    public void update() {
    }

    @Override
    public int getID() {
        return id;
    }
}
