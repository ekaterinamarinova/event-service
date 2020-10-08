package service.definition;

import event.definition.LoggingEvent;

import java.util.List;

public interface StorageService {
    void storeEventsInCSV(List<LoggingEvent> events);
}
