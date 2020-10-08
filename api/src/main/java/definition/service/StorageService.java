package definition.service;


import definition.event.LoggingEvent;

import java.util.List;

public interface StorageService {
    void storeEventsInCSV(List<LoggingEvent> events);
}
