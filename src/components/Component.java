package components;

import others.MessageChannel;

public interface Component {
    public abstract int getID();

    public abstract void process(MessageChannel channel);

    public abstract void receive(String command);

    public abstract void update();
}
