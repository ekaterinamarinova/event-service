package service;

import definition.event.LoggingEvent;
import definition.service.LoggingService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

@Component(service = LoggingService.class)
public class LoggingServiceImpl implements LoggingService {

    private List<LoggingEvent> loggingEvents;
    private MonitoringServiceImpl monitoringService;

    @Override
    public synchronized void logEvent(LoggingEvent loggingEvent) {
        loggingEvents.add(loggingEvent);
        monitoringService.notifyMonitoringListeners(loggingEvent);
    }

    @Reference
    public void setMonitoringService(MonitoringServiceImpl monitoringService) {
        this.monitoringService = monitoringService;
    }

    @Reference
    public void setLoggingEvents(List<LoggingEvent> loggingEvents) {
        this.loggingEvents = loggingEvents;
    }

}