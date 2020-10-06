package service.definition;

import util.EventType;

public interface LoggingService {
    void logEvent(EventType eventType, String s);
}
