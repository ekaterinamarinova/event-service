package activator;

import definition.event.EventType;
import org.osgi.util.tracker.ServiceTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import definition.service.MonitoringService;
import definition.service.RetrievingService;
import org.osgi.framework.*;
import storageService.StorageService;

import java.time.LocalTime;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class EventActivator implements BundleActivator {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventActivator.class);

    private BundleContext ctx;
    private final StorageService storageService = new StorageService();

    private ServiceTracker<RetrievingService, RetrievingService> retrievingServiceTracker;
    private ServiceTracker<MonitoringService, MonitoringService> monitoringServiceTracker;

    public void start(BundleContext ctx) {
        this.ctx = ctx;
        startTrack(ctx);
    }

    public void stop(BundleContext ctx) {
        stopTrack();
        this.ctx = null;
    }

    private void startTrack(BundleContext ctx) {
        monitoringServiceTracker = new ServiceTracker<>(ctx, MonitoringService.class, null);
        retrievingServiceTracker = new ServiceTracker<>(ctx, RetrievingService.class, null);

        monitoringServiceTracker.open();
        retrievingServiceTracker.open();

        if (!monitoringServiceTracker.isEmpty()) {
            monitoringServiceTracker.getService().addMonitoringListener(event -> LOGGER.info(
                    "Event received: " + event.getEventType() + " with message: " + event.getMessage()
                    )
            );
        }

        if (!retrievingServiceTracker.isEmpty()) {
            ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
            scheduledExecutorService.scheduleAtFixedRate(() ->
            {
                try {
                    storageService.storeEventsInCSV(retrievingServiceTracker.getService().retrieve(
                            EventType.Info, LocalTime.now().minusSeconds(120), LocalTime.now()
                    ));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }, 0, 5, TimeUnit.MINUTES);
        }
    }

    private void stopTrack() {
        monitoringServiceTracker.close();
        retrievingServiceTracker.close();
    }


}