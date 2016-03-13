package components.spells;

import org.newdawn.slick.SlickException;

import components.Component;
import components.HealthComponent;
import components.SpriteComponent;
import components.TransformComponent;
import others.Consts;
import others.Entity;
import others.MessageChannel;

public class SplitComponent implements Component {
    private int id = Consts.SPLIT;
    private Entity self;

    private int piecesCount;
    private boolean splitting;
    private float angle;

    public SplitComponent(Entity self, int piecesCount, float angle) {
        this.self = self;
        this.piecesCount = piecesCount;
        this.angle = angle;
    }

    public void process(MessageChannel channel) {
    }

    public void receive(String command) {
        String str = command;
        if (str.matches("split")) {
            splitting = true;
            return;
        }
    }

    public Entity split() throws SlickException {
        Entity entity1 = new Entity("CoFireball");
        entity1.addComponent(new SpriteComponent(entity1, "images/spells/cofireball-1.png", 64, 64));
        entity1.addComponent(new GuideComponent(entity1, 5.5f, (float) (Math.PI - angle + Math.random() * 20)));
        entity1.addComponent(new ImpactComponent(entity1));
        entity1.addComponent(new SplitComponent(entity1, piecesCount - 1, (float) (Math.PI / 2 - angle)));
        entity1.addComponent(new TransformComponent(entity1, 300, 200, 64, 64));

        return entity1;
    }

    public boolean isSplitting() {
        return splitting;
    }

    public void update() {

    }

    public int getPiecesCount() {
        return piecesCount;
    }

    public int getID() {
        return id;
    }
}
