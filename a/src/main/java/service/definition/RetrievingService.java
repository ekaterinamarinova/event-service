package service.definition;

import event.definition.EventType;
import event.definition.LoggingEvent;

import java.time.LocalTime;
import java.util.List;

public interface RetrievingService {
    List<LoggingEvent> retrieve(EventType eventType, LocalTime startTime, LocalTime endTime);
}
