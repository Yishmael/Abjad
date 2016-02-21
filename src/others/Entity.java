package others;

import java.util.ArrayList;

import components.Component;

public class Entity {
    ArrayList<Component> comps = new ArrayList<Component>();
    private long id = 0l;
    private String name;

    public Entity(String name) {
        this.name = name;
    }

    public void addComponent(Component comp) {
        comps.add(comp);
        id = id | (long) Math.pow(2, comp.getBit());
        // System.out.println(Long.toString(id, 2));
    }

    public Component getComponent(int bit) {
        for (Component comp: comps) {
            if (comp.getBit() == bit)
                return comp;
        }
        return null;
    }

    public ArrayList<Component> getComponents() {
        return comps;
    }

    public long getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean hasComponent(int bit) {
        return id == (id | (long) Math.pow(2, bit));
    }

    public void process(MessageChannel channel) {
        for (Component component: comps) {
            component.process(channel);
        }
    }

    public void removeComponent(int bit) {
        if (this.hasComponent(bit)) {
            comps.remove(bit);
            id ^= bit;
        }
    }
}
