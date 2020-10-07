package event;

public enum EventType {
    SERVICE_REGISTERED("ServiceRegisteredEvent", "A service has just been registered.");

    private final String type;
    private final String message;

    EventType(String type, String message) {
        this.type = type;
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }
}
