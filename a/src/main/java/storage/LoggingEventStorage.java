package storage;


import definition.event.LoggingEvent;

import java.util.ArrayList;
import java.util.List;

public final class LoggingEventStorage {
    private final List<LoggingEvent> loggingEventList;

    private LoggingEventStorage() {
        loggingEventList = new ArrayList<>();
    }

    public static LoggingEventStorage getInstance() {
        return new LoggingEventStorage();
    }

    public List<LoggingEvent> getLoggingEventList() {
        return loggingEventList;
    }
}
