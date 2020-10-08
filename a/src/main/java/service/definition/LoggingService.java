package service.definition;

import event.definition.LoggingEvent;

public interface LoggingService {
    void logEvent(LoggingEvent loggingEvent);
}
