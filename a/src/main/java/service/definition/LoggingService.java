package service.definition;

import event.definition.Event;

import java.util.List;

public interface LoggingService {
    void logEvent(Event event);
    List<Event> getEvents();
}
