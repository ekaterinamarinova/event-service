package service;

import definition.event.EventType;
import definition.service.MonitoringService;
import definition.service.RetrievingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledEventService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledEventService.class);
    private final StorageService storageService = new StorageService();

    private final RetrievingService retrievingService;
    private final MonitoringService monitoringService;

    public ScheduledEventService(RetrievingService retrievingService, MonitoringService monitoringService) {
        this.retrievingService = retrievingService;
        this.monitoringService = monitoringService;
    }

    public void start() {
        monitoringService.addMonitoringListener(event -> LOGGER.info(
                "Event received: " + event.getEventType() + " with message: " + event.getMessage()
                )
        );

        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
        scheduledExecutorService.scheduleAtFixedRate(() ->
        {
            try {
                storageService.storeEventsInCSV(retrievingService.retrieve(
                        EventType.Info, LocalTime.now().minusSeconds(120), LocalTime.now()
                ));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 5, TimeUnit.MINUTES);
    }

}