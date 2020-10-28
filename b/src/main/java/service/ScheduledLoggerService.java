package service;

import definition.event.EventType;
import definition.service.LoggingService;
import implementation.event.LoggingEventImpl;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.time.LocalTime;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class ScheduledLoggerService {

    @Reference
    private LoggingService loggingService;

    @Activate
    public void start() {
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
        scheduledExecutorService.scheduleAtFixedRate(() -> loggingService.logEvent(
                new LoggingEventImpl(EventType.Info, "Event Registered", LocalTime.now())
        ), 0, 1000, TimeUnit.MILLISECONDS);
    }

}
