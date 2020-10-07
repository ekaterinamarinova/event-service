package activator;

import event.definition.EventType;
import listener.MonitoringListener;
import org.osgi.framework.*;
import service.definition.MonitoringService;
import service.definition.RetrievingService;

import java.time.LocalTime;

public class EventActivator implements BundleActivator, ServiceListener {

    private BundleContext ctx;
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
                monitoringService.addMonitoringListener(new MonitoringListener());

                RetrievingService retrievingService = ctx.getService(retrievingServiceServiceReference);
                retrievingService.retrieve(EventType.SERVICE_REGISTERED.getType(), LocalTime.now().minusSeconds(120), LocalTime.now());
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