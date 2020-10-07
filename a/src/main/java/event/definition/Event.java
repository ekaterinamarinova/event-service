package event.definition;

import event.EventType;

import java.time.LocalTime;

public interface Event {

    LocalTime getCreationTime();

    EventType getEventType();

}
