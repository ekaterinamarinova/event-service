package service.implementation;

import definition.event.EventType;
import definition.event.LoggingEvent;
import definition.service.RetrievingService;
import storage.LoggingEventStorage;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class RetrievingServiceImpl implements RetrievingService {

    private final List<LoggingEvent> loggingEventTypeList;

    public RetrievingServiceImpl(LoggingEventStorage loggingEventStorage) {
        this.loggingEventTypeList = loggingEventStorage.getLoggingEventList();
    }

    public List<LoggingEvent> retrieve(EventType eventType, LocalTime startTime, LocalTime endTime) {
        return loggingEventTypeList.stream()
                .filter(e -> e.getEventType().equals(eventType) &&
                        e.getCreationTime().isAfter(startTime) &&
                        e.getCreationTime().isBefore(endTime))
                .collect(Collectors.toList());
    }
}
