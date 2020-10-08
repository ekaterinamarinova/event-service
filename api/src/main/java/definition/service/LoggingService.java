package definition.service;

import definition.event.LoggingEvent;

public interface LoggingService {
    void logEvent(LoggingEvent loggingEvent);
}
