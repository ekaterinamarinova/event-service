package activator;

import definition.event.EventType;
import definition.service.MonitoringService;
import definition.service.RetrievingService;
import definition.service.StorageService;
import org.osgi.framework.*;
import service.implementation.StorageServiceImpl;

import java.time.LocalTime;
import java.util.logging.Logger;

public class EventActivator implements BundleActivator, ServiceListener {

    private static final Logger LOGGER = Logger.getLogger(EventActivator.class.getName());

    private BundleContext ctx;
    private final StorageService storageService = new StorageServiceImpl();
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
                System.out.println("Notification of service registered.");
                monitoringServiceServiceReference = (ServiceReference<MonitoringService>) serviceEvent.getServiceReference();
                retrievingServiceServiceReference = (ServiceReference<RetrievingService>) serviceEvent.getServiceReference();

                MonitoringService monitoringService = ctx.getService(monitoringServiceServiceReference);
                monitoringService.addMonitoringListener(event -> LOGGER.info(
                        "Event received: " + event.getEventType() + " with message: " + event.getMessage()
                        )
                );

                RetrievingService retrievingService = ctx.getService(retrievingServiceServiceReference);
                storageService.storeEventsInCSV(
                        retrievingService.retrieve(
                                EventType.Info, LocalTime.now().minusSeconds(120), LocalTime.now()
                        )
                );

                break;
            case (ServiceEvent.UNREGISTERING):
                System.out.println("Notification of service unregistered.");
                ctx.ungetService(serviceEvent.getServiceReference());
                break;
            default:
                break;
        }
    }
}