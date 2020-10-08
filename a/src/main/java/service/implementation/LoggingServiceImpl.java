package service.implementation;

import event.definition.LoggingEvent;
import service.definition.LoggingService;
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