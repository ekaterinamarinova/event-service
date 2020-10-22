package service;

import definition.event.LoggingEvent;
import definition.service.LoggingService;

import java.util.List;

public class LoggingServiceImpl implements LoggingService {

    private List<LoggingEvent> loggingEvents;
    private final MonitoringServiceImpl monitoringService;

    public LoggingServiceImpl(List<LoggingEvent> loggingEvents, MonitoringServiceImpl monitoringService) {
        this.loggingEvents = loggingEvents;
        this.monitoringService = monitoringService;
    }

    public LoggingServiceImpl(MonitoringServiceImpl monitoringService) {
        this.monitoringService = monitoringService;
    }

    @Override
    public synchronized void logEvent(LoggingEvent loggingEvent) {
        loggingEvents.add(loggingEvent);
        monitoringService.notifyMonitoringListeners(loggingEvent);
    }

}