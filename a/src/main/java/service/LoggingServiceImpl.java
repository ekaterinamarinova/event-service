package service;

import definition.event.LoggingEvent;
import definition.service.LoggingService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

@Component(service = LoggingService.class)
public class LoggingServiceImpl implements LoggingService {

    @Reference(unbind = "clear")
    private List<LoggingEvent> loggingEvents;
    @Reference
    private MonitoringServiceImpl monitoringService;

    @Override
    public synchronized void logEvent(LoggingEvent loggingEvent) {
        loggingEvents.add(loggingEvent);
        monitoringService.notifyMonitoringListeners(loggingEvent);
    }


}