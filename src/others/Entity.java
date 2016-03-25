package others;

import java.util.ArrayList;

import components.Component;

public class Entity {
    private ArrayList<Component> comps = new ArrayList<Component>();
    private long id = 0x00000000;
    private String name;

    public Entity(String name) {
        this.name = name;
    }

    public void addComponent(Component comp) {
        if (!this.hasComponent(comp.getID())) {
            comps.add(comp);
            broadcast("added " + comp.getID());
            id = id | comp.getID();
        }
    }

    public void broadcast(String command) {
        // System.out.println(name + ":" +command);
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

    public Component getComponent(long id) {
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

    public void setName(String name) {
        this.name = name;
    }

    public boolean hasComponent(long id) {
        return this.id == (this.id | id);
    }

    public void removeComponent(long id) {
        if (this.hasComponent(id)) {
            comps.remove(getComponent(id));
            broadcast("removed " + id);
            this.id &= ~id;
        }
    }
}
