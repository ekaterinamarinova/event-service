package activator;

import definition.event.EventType;
import definition.service.LoggingService;
import implementation.event.LoggingEventImpl;
import org.osgi.framework.*;

import java.time.LocalTime;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class LoggingActivator implements BundleActivator, ServiceListener {

    private BundleContext ctx;
    private ServiceReference serviceReference;

    public void start(BundleContext ctx) {
        this.ctx = ctx;
        try {
            ctx.addServiceListener(this, "(objectclass=" + LoggingService.class.getName() + ")");
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
                serviceReference = serviceEvent.getServiceReference();
                LoggingService service = (LoggingService) (ctx.getService(serviceReference));
                //start a thread and generate 100 events per second
                ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
                scheduledExecutorService.scheduleAtFixedRate(() -> service.logEvent(new LoggingEventImpl(
                        EventType.Info, "Event Registered", LocalTime.now()
                )), 500, 1000, TimeUnit.MILLISECONDS);
                break;
            case (ServiceEvent.UNREGISTERING):
                ctx.ungetService(serviceEvent.getServiceReference());
                break;
            default:
                break;
        }
    }
}
