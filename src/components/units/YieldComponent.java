package components.units;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;

import components.Component;
import others.Consts;
import others.Entity;
import others.EntityFactory;
import others.MessageChannel;

public class YieldComponent implements Component {
    private long id = Consts.YIELD;
    private Entity self;

    private ArrayList<Entity> drops = new ArrayList<Entity>();
    private boolean yielded;
    private int level = 1;

    public YieldComponent(Entity self) {
        this.self = self;
    }

    public YieldComponent(Entity self, ArrayList<Entity> drops) {
        this.self = self;
        for (Entity drop: drops) {
            if (drop != null) {
                this.drops.add(drop);
            }
        }
    }

    public ArrayList<Entity> getDrops() throws SlickException {
        self.broadcast("requestlvl");
        if (Math.random() < 0.1) {
            drops.add(EntityFactory.getAxeBlueprint(level));
        }
        if (Math.random() < 0.1) {
            drops.add(EntityFactory.getBootsBlueprint(level));
        }
        if (Math.random() < 0.1) {
            drops.add(EntityFactory.getWandBlueprint(level));
        }
        if (Math.random() < 0.1) {
            drops.add(EntityFactory.getCherryBlueprint(level));
        }
        if (Math.random() < 0.1) {
            drops.add(EntityFactory.getChestBlueprint(level));
        }
        if (Math.random() < 0.1) {
            drops.add(EntityFactory.getGlovesBlueprint(level));
        }
        yielded = true;

        return drops;
    }

    public boolean hasYielded() {
        return yielded;
    }

    @Override
    public void process(MessageChannel channel) {
    }

    @Override
    public void receive(String command) {
        String str = command;
        if (str.matches("lvl [0-9]+")) {
            level = Integer.parseInt(str.substring(4));
        }
    }

    @Override
    public void update() {
    }

    @Override
    public long getID() {
        return id;
    }
}
