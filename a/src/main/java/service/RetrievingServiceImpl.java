package service;

import definition.event.EventType;
import definition.event.LoggingEvent;
import definition.service.RetrievingService;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RetrievingServiceImpl implements RetrievingService {

    private final List<LoggingEvent> loggingEventTypeList;

    public RetrievingServiceImpl(List<LoggingEvent> loggingEvents) {
        this.loggingEventTypeList = loggingEvents;
    }

    public synchronized List<LoggingEvent> retrieve(EventType eventType,
                                                    LocalTime startTime,
                                                    LocalTime endTime) throws IllegalAccessException {
        if(Objects.isNull(startTime) || Objects.isNull(endTime))
            throw new IllegalAccessException("Method parameters cannot be null!");
        return loggingEventTypeList.stream()
                .filter(e -> e.getEventType().equals(eventType) &&
                        e.getCreationTime().isAfter(startTime) &&
                        e.getCreationTime().isBefore(endTime))
                .collect(Collectors.toList());
    }
}
