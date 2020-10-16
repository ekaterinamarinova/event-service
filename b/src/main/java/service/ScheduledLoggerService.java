package service;

import definition.event.EventType;
import definition.service.LoggingService;
import implementation.event.LoggingEventImpl;

import java.time.LocalTime;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledLoggerService {

    private final LoggingService loggingService;

    public ScheduledLoggerService(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    public void start() {
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
        scheduledExecutorService.scheduleAtFixedRate(() -> loggingService.logEvent(
                new LoggingEventImpl(EventType.Info, "Event Registered", LocalTime.now())
        ), 0, 1000, TimeUnit.MILLISECONDS);
    }

}
