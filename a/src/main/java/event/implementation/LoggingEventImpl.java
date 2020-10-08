package event.implementation;

import event.definition.EventType;
import event.definition.LoggingEvent;

import java.time.LocalTime;
import java.util.Objects;

public class LoggingEventImpl implements LoggingEvent {

    private final EventType eventType;
    private final String message;
    private final LocalTime creationTime;

    public LoggingEventImpl(EventType type, String message, LocalTime creationTime) {
        this.eventType = type;
        this.message = message;
        this.creationTime = creationTime;
    }

    @Override
    public EventType getEventType() {
        return this.eventType;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public LocalTime getCreationTime() {
        return creationTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoggingEventImpl that = (LoggingEventImpl) o;
        return eventType == that.eventType &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventType, message);
    }
}
