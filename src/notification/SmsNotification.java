package notification;

public class SmsNotification extends Notification {
    private String phoneNumber;

    public SmsNotification(String id, long timestamp, String content, String phoneNumber) {
        super(id, timestamp, content);
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "SMS [" + getTimestamp() + "] to " + phoneNumber + ": " + getContent();
    }
}

