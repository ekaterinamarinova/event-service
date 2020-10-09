package activator;

import definition.event.EventType;
import definition.service.MonitoringService;
import definition.service.RetrievingService;
import implementation.event.LoggingEventImpl;
import implementation.event.Scheduler;
import org.osgi.framework.*;
import service.StorageService;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class EventActivator implements BundleActivator, ServiceListener {

    private static final Logger LOGGER = Logger.getLogger(EventActivator.class.getName());

    private BundleContext ctx;
    private final StorageService storageService = new StorageService();
    private ServiceReference<MonitoringService> monitoringServiceServiceReference;
    private ServiceReference<RetrievingService> retrievingServiceServiceReference;


    public void start(BundleContext ctx) {
        this.ctx = ctx;
        try {
            ctx.addServiceListener(this, "(objectclass=" + MonitoringService.class.getName() + ")");
            ctx.addServiceListener(this, "(objectclass=" + RetrievingService.class.getName() + ")");
        } catch (InvalidSyntaxException ise) {
            ise.printStackTrace();
        }
    }

    public void stop(BundleContext bundleContext) {
        if (monitoringServiceServiceReference != null &&
                retrievingServiceServiceReference != null) {
            ctx.ungetService(monitoringServiceServiceReference);
            ctx.ungetService(retrievingServiceServiceReference);
        }
        this.ctx = null;
    }

    public void serviceChanged(ServiceEvent serviceEvent) {
        int type = serviceEvent.getType();
        switch (type) {
            case (ServiceEvent.REGISTERED):
                Scheduler scheduler = new Scheduler();

                monitoringServiceServiceReference = (ServiceReference<MonitoringService>) serviceEvent.getServiceReference();
                retrievingServiceServiceReference = (ServiceReference<RetrievingService>) serviceEvent.getServiceReference();

                MonitoringService monitoringService = ctx.getService(monitoringServiceServiceReference);
                monitoringService.addMonitoringListener(event -> LOGGER.info(
                        "Event received: " + event.getEventType() + " with message: " + event.getMessage()
                        )
                );

                // TODO retrieve and export to csv in a separate thread.
                RetrievingService retrievingService = ctx.getService(retrievingServiceServiceReference);

                scheduler.scheduleEventExecution(() -> retrievingService.retrieve(
                        EventType.Info, LocalTime.now().minusSeconds(120), LocalTime.now()
                ), 1, 5, TimeUnit.MINUTES);

                break;
            case (ServiceEvent.UNREGISTERING):
                ctx.ungetService(serviceEvent.getServiceReference());
                break;
            default:
                break;
        }
    }


}