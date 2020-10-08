package definition.service;


import definition.event.EventType;
import definition.event.LoggingEvent;

import java.time.LocalTime;
import java.util.List;

public interface RetrievingService {
    List<LoggingEvent> retrieve(EventType eventType, LocalTime startTime, LocalTime endTime);
}
