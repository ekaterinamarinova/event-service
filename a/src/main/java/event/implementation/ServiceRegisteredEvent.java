package event.implementation;

import event.definition.EventType;
import event.definition.Event;

import java.time.LocalTime;

public class ServiceRegisteredEvent implements Event {

    private final LocalTime creationTime;
    private final EventType eventType;

    public ServiceRegisteredEvent(LocalTime creationTime) {
        this.creationTime = creationTime;
        this.eventType = EventType.SERVICE_REGISTERED;
    }

    @Override
    public LocalTime getCreationTime() {
        return this.creationTime;
    }

    @Override
    public EventType getEventType() {
        return this.eventType;
    }
}
