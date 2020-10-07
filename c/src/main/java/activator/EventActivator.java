package activator;

import event.definition.EventType;
import listener.implementation.MonitoringListenerImpl;
import org.osgi.framework.*;
import service.definition.LoggingService;
import service.definition.MonitoringService;
import service.definition.RetrievingService;
import service.definition.StorageService;
import service.implementation.StorageServiceImpl;

import java.time.LocalTime;

public class EventActivator implements BundleActivator, ServiceListener {

    private BundleContext ctx;
    private final StorageService storageService = new StorageServiceImpl();
    private ServiceReference<MonitoringService> monitoringServiceServiceReference;
    private ServiceReference<RetrievingService> retrievingServiceServiceReference;
    private ServiceReference<LoggingService> loggingServiceServiceReference;


    public void start(BundleContext ctx) {
        this.ctx = ctx;
        try {
            ctx.addServiceListener(this, "(objectclass=" + MonitoringService.class.getName() + ")");
            ctx.addServiceListener(this, "(objectclass=" + RetrievingService.class.getName() + ")");
            ctx.addServiceListener(this, "(objectclass=" + LoggingService.class.getName() + ")");
        } catch (InvalidSyntaxException ise) {
            ise.printStackTrace();
        }
    }

    public void stop(BundleContext bundleContext) {
        if (monitoringServiceServiceReference != null &&
            retrievingServiceServiceReference != null &&
            loggingServiceServiceReference != null) {
            ctx.ungetService(monitoringServiceServiceReference);
            ctx.ungetService(retrievingServiceServiceReference);
            ctx.ungetService(loggingServiceServiceReference);
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
                loggingServiceServiceReference = (ServiceReference<LoggingService>) serviceEvent.getServiceReference();

                //TODO: check whether getting a service can occur only once before it's been ungot
                LoggingService loggingService = ctx.getService(loggingServiceServiceReference);

                MonitoringService monitoringService = ctx.getService(monitoringServiceServiceReference);
                monitoringService.addMonitoringListener(new MonitoringListenerImpl(loggingService));

                monitoringService.notifyListenerToMonitorEvents();

                RetrievingService retrievingService = ctx.getService(retrievingServiceServiceReference);
                storageService.storeEventsInCSV(
                        retrievingService.retrieve(
                            EventType.SERVICE_REGISTERED.getType(), LocalTime.now().minusSeconds(120), LocalTime.now()
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