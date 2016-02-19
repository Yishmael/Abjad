package entities;

import java.util.ArrayList;

import components.Component;

public class Entity {
	ArrayList<Component> comps = new ArrayList<Component>();
	private int id;

	public Entity(int id) {
		this.id = id;
	}

	public int getID() {
		return id;
	}

	public void addComponent(Component comp) {
		comps.add(comp);
	}

	public void removeComponent(Component comp) {
		if (hasComponent(comp)) {
			comps.remove(comp);
		}
	}
	
	public ArrayList<Component> getComponents() {
	    return comps;
	}

	public boolean hasComponent(Component comp) {
		return comps.contains(comp);
	}
}
