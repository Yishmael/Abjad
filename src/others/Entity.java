package others;

import java.util.ArrayList;

import components.Component;

public class Entity {
    private ArrayList<Component> comps = new ArrayList<Component>();
    private long id = 0l;
    private String name;

    public Entity(String name) {
        this.name = name;
    }

    public void addComponent(Component comp) {
        comps.add(comp);
        broadcast("added " + comp.getBit());
        id = id | (long) Math.pow(2, comp.getBit());
    }

    public void broadcast(String command) {
        // System.out.println(command);
        if (command == null) {
            return;
        }
        for (Component component: comps) {
            component.receive(command);
        }
    }

    // TODO make components pass lists of commands for more complex actions
    public void broadcast(String[] commands) {
        // System.out.println(commands);
        if (commands == null) {
            return;
        }
        for (String command: commands) {
            for (Component component: comps) {
                component.receive(command);
            }
        }
    }

    public void process(MessageChannel channel) {
        // System.out.println(channel.getCommand());
        if (channel.getCommand() == null) {
            return;
        }
        for (Component component: comps) {
            component.process(channel);
        }
    }

    public Component getComponent(int bit) {
        // System.out.println(bit);
        for (Component comp: comps) {
            if (comp.getBit() == bit)
                return comp;
        }
        return null;
    }

    public ArrayList<Component> getComponents() {
        return comps;
    }

    public String getName() {
        return name;
    }

    public boolean hasComponent(int bit) {
        return id == (id | (long) Math.pow(2, bit));
    }

    public void removeComponent(int bit) {
        if (this.hasComponent(bit)) {
            comps.remove(bit);
            broadcast("removed " + bit);
            id ^= bit;
        }
    }
}
