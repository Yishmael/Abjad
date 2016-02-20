package others;

public class MessageChannel {
    private Entity sender;
    private String command;

    public MessageChannel(Entity sender, String command) {
        this.sender = sender;
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public Entity getSender() {
        return sender;
    }
}
