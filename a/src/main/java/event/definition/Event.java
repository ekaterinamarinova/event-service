package event.definition;

import java.time.LocalTime;

public interface Event {

    LocalTime getCreationTime();

    EventType getEventType();

}
