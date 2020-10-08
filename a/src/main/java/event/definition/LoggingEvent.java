package event.definition;

import java.time.LocalTime;

public interface LoggingEvent {

    EventType getEventType();

    String getMessage();

    LocalTime getCreationTime();
}
