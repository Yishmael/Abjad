package components.spells;

import org.newdawn.slick.SlickException;

import components.Component;
import components.units.SpriteComponent;
import others.Consts;
import others.Entity;
import others.MessageChannel;

public class SummonComponent implements Component {
    private long id = Consts.SUMMON;
    private Entity self;

    private boolean ready = false;
    private int count;
    private float angle;

    public SummonComponent(Entity self, int count, float angle) {
        this.self = self;
        this.count = count;
        this.angle = angle;
    }

    public void process(MessageChannel channel) {

    }

    public void receive(String command) {
        String str = command;
        if (str.matches("trigger")) {
            ready = true;
        }
    }

    public Entity summon() throws SlickException {
        Entity entity1 = new Entity("CoFireball");

        entity1.addComponent(new SpriteComponent(entity1, "images/ifrit.png", 64, 64));
        entity1.addComponent(new SpriteComponent(entity1, "images/ifrit.png", 64, 64));

        // entity1.addComponent(new GuideComponent(entity1, 5.5f, (float)
        // (Math.PI - angle + Math.random() * 20)));
        // entity1.addComponent(new SummonComponent(entity1, 0, (float) (Math.PI
        // / 2 - angle)));

        return entity1;
    }

    public boolean isReady() {
        return ready;
    }

    public void update() {

    }

    public int getCount() {
        return count;
    }

    public long getID() {
        return id;
    }
}
