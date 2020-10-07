package service.implementation;

import event.definition.Event;
import service.definition.LoggingService;
import service.definition.RetrievingService;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RetrievingServiceImpl implements RetrievingService {

    private LoggingService loggingService;
    private List<Event> eventTypeList;

    public RetrievingServiceImpl(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    public List<String> retrieve(String eventType, LocalTime startTime, LocalTime endTime) {
        if (loggingService.getEvents() == null) return new ArrayList<>();
        eventTypeList = loggingService.getEvents();

        return eventTypeList.stream()
                .filter(e -> e.getEventType().getType().equals(eventType) &&
                        e.getCreationTime().isAfter(startTime) &&
                        e.getCreationTime().isBefore(endTime))
                .map(event -> eventType)
                .collect(Collectors.toList());
    }
}
