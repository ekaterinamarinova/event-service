package activator;

import definition.event.EventType;
import definition.service.LoggingService;
import implementation.event.LoggingEventImpl;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

import java.time.LocalTime;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class LoggingActivator implements BundleActivator {
    private ServiceTracker<LoggingService, LoggingService> loggingServiceTracker;

    public void start(BundleContext ctx) {
        loggingServiceTracker = new ServiceTracker<>(ctx, LoggingService.class, null);
        loggingServiceTracker.open();

        if (!loggingServiceTracker.isEmpty()) {
            ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
            scheduledExecutorService.scheduleAtFixedRate(() -> loggingServiceTracker.getService().logEvent(
                    new LoggingEventImpl(EventType.Info, "Event Registered", LocalTime.now())
            ), 0, 1000, TimeUnit.MILLISECONDS);
        }
    }

    public void stop(BundleContext ctx) {
        this.loggingServiceTracker.close();
    }
}
