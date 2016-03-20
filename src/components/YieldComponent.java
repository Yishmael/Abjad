package components;

import java.util.ArrayList;

import others.Consts;
import others.Entity;
import others.MessageChannel;

public class YieldComponent implements Component {
    private int id = Consts.YIELD;
    private Entity self;

    private ArrayList<Entity> drops = new ArrayList<Entity>();
    private boolean yielded;

    public YieldComponent(Entity self, ArrayList<Entity> drops) {
        this.self = self;
        for (Entity drop: drops) {
            if (drop != null) {
                this.drops.add(drop);
            }
        }
    }

    public ArrayList<Entity> getDrops() {
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

    }

    @Override
    public void update() {
    }

    @Override
    public int getID() {
        return id;
    }
}
