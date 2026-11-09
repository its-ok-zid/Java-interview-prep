package notification;

public class EmailNotification extends Notification {
    private String emailAddress;

    public EmailNotification(String id, long timestamp, String content, String emailAddress) {
        super(id, timestamp, content);
        this.emailAddress = emailAddress;
    }

    @Override
    public String toString() {
        return "EMAIL [" + getTimestamp() + "] to " + emailAddress + ": " + getContent();
    }
}