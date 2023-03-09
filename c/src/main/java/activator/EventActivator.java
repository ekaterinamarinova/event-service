package activator;

import definition.event.EventType;
import definition.event.LoggingEvent;
import definition.service.MonitoringService;
import definition.service.RetrievingService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import storageService.StorageService;

import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class EventActivator implements BundleActivator {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventActivator.class);

    private final StorageService storageService = new StorageService();

    private ServiceTracker<RetrievingService, RetrievingService> retrievingServiceTracker;
    private ServiceTracker<MonitoringService, MonitoringService> monitoringServiceTracker;

    public void start(BundleContext ctx) {
        startTrack(ctx);
    }

    public void stop(BundleContext ctx) {
        stopTrack();
    }

    private void startTrack(BundleContext ctx) {
        monitoringServiceTracker = new ServiceTracker<>(ctx, MonitoringService.class, null);
        retrievingServiceTracker = new ServiceTracker<>(ctx, RetrievingService.class, null);

        monitoringServiceTracker.open();
        retrievingServiceTracker.open();

        if (!monitoringServiceTracker.isEmpty()) {
            monitoringServiceTracker.getService().addMonitoringListener(
                    event -> LOGGER.info(
                    "Event received: " + event.getEventType() + " with message: " + event.getMessage()
                    )
            );
        }

        if (!retrievingServiceTracker.isEmpty()) {
            ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
            scheduledExecutorService.scheduleAtFixedRate(() ->
            {
                try {
                    List<LoggingEvent> eventList = retrievingServiceTracker.getService()
                            .retrieve(EventType.Info, LocalTime.now().minusSeconds(120), LocalTime.now());

                    storageService.storeEventsInCSV(eventList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, 0, 1, TimeUnit.MINUTES);
        }
    }

    private void stopTrack() {
        monitoringServiceTracker.close();
        retrievingServiceTracker.close();
    }

}