package service.implementation;

import definition.event.LoggingEvent;
import definition.service.LoggingService;
import storage.LoggingEventStorage;

import java.util.List;

public class LoggingServiceImpl implements LoggingService {

    private List<LoggingEvent> loggingEvents;

    public LoggingServiceImpl(LoggingEventStorage loggingEventStorage) {
        this.loggingEvents = loggingEventStorage.getLoggingEventList();
    }

    @Override
    public void logEvent(LoggingEvent loggingEvent) {
        loggingEvents.add(loggingEvent);
    }


}