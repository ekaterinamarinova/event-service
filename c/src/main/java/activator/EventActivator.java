package activator;

import listener.MonitoringListener;
import org.osgi.framework.*;
import service.definition.MonitoringService;
import service.definition.RetrievingService;

import java.time.LocalTime;

public class EventActivator implements BundleActivator, ServiceListener {

    private BundleContext ctx;
    private ServiceReference serviceReference;

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
        if (serviceReference != null) {
            ctx.ungetService(serviceReference);
        }
        this.ctx = null;
    }

    public void serviceChanged(ServiceEvent serviceEvent) {
        int type = serviceEvent.getType();
        switch (type) {
            case (ServiceEvent.REGISTERED):
                System.out.println("Notification of service registered.");
                serviceReference = serviceEvent.getServiceReference();
                if (ctx.getService(serviceReference) instanceof MonitoringService) {
                    MonitoringService service = (MonitoringService) (ctx.getService(serviceReference));
//                    service.addMonitoringListener(new MonitoringListener());
                }

                if (ctx.getService(serviceReference) instanceof RetrievingService) {
                    RetrievingService service = (RetrievingService) (ctx.getService(serviceReference));
                    service.retrieve(null, LocalTime.now(), LocalTime.now());
                }

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