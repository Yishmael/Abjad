package components;

import enums.ItemType;
import others.Consts;
import others.Entity;
import others.MessageChannel;

public class YieldComponent implements Component {
    private int id = Consts.YIELD;
    private Entity entity;

    private ItemType[] drops;

    public YieldComponent(Entity entity, ItemType[] drops) {
        this.entity = entity;
        this.drops = new ItemType[drops.length];
        for (int i = 0; i < drops.length; i++) {
            this.drops[i] = drops[i];
        }
    }

    public ItemType[] getDrops() {
        return drops;
    }

    public void process(MessageChannel channel) {
    }

    public void receive(String command) {
        // TODO Auto-generated method stub

    }

    public void update() {
    }

    public int getID() {
        return id;
    }
}
