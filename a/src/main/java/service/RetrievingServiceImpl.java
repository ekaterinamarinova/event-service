package service;

import definition.event.EventType;
import definition.event.LoggingEvent;
import definition.service.RetrievingService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component(service = RetrievingService.class)
public class RetrievingServiceImpl implements RetrievingService {

    @Reference(unbind = "clear")
    private List<LoggingEvent> loggingEvents;

    @Override
    public synchronized List<LoggingEvent> retrieve(EventType eventType,
                                                    LocalTime startTime,
                                                    LocalTime endTime) throws Exception {
        if (Objects.isNull(startTime) || Objects.isNull(endTime))
            throw new IllegalArgumentException("Method parameters cannot be null!");
        if (Objects.isNull(loggingEvents)) throw new Exception("List mustn't be empty!");
        return loggingEvents.stream()
                .filter(e -> e.getEventType().equals(eventType) &&
                        e.getCreationTime().isAfter(startTime) &&
                        e.getCreationTime().isBefore(endTime))
                .collect(Collectors.toList());
    }

}
