package others;

import java.util.ArrayList;

import components.Component;

public class Entity {
    private ArrayList<Component> comps = new ArrayList<Component>();
    private int id = 0x00000000;
    private String name;

    public Entity(String name) {
        this.name = name;
    }

    public void addComponent(Component comp) {
        comps.add(comp);
        broadcast("added " + comp.getID());
        id = id | comp.getID();
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
        if (commands == null || commands.length == 0) {
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

    public Component getComponent(int id) {
        // System.out.println(bit);
        for (Component comp: comps) {
            if (comp.getID() == id)
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

    public boolean hasComponent(int id) {
        return this.id == (this.id | id);
    }

    public void removeComponent(int id) {
        if (this.hasComponent(id)) {
            comps.remove(getComponent(id));
            broadcast("removed " + id);
            this.id &= ~id;
        }
    }
}
