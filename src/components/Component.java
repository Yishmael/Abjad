package components;

import others.MessageChannel;

public interface Component {
    public abstract void update();

    public abstract void receive(String command);

    public abstract void process(MessageChannel channel);

    public abstract long getID();
}
