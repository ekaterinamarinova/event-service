package activator;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import service.definition.LoggingService;
import service.definition.MonitoringService;
import service.definition.RetrievingService;
import service.implementation.LoggingServiceImpl;
import service.implementation.MonitoringServiceImpl;
import service.implementation.RetrievingServiceImpl;

import java.util.Hashtable;

public class Activator implements BundleActivator {

    private ServiceReference<LoggingService> loggingServiceServiceReference;
    private ServiceRegistration<LoggingService> loggingServiceServiceRegistration;

    private ServiceReference<MonitoringService> monitoringServiceServiceReference;
    private ServiceRegistration<MonitoringService> monitoringServiceServiceRegistration;

    private ServiceReference<RetrievingService> retrievingServiceServiceReference;
    private ServiceRegistration<RetrievingService> retrievingServiceServiceRegistration;

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        loggingServiceServiceRegistration = bundleContext.registerService(
                LoggingService.class,
                new LoggingServiceImpl(),
                new Hashtable<>()
        );

        loggingServiceServiceReference = loggingServiceServiceRegistration.getReference();

        monitoringServiceServiceRegistration = bundleContext.registerService(
                MonitoringService.class,
                new MonitoringServiceImpl(),
                new Hashtable<>()
        );

        monitoringServiceServiceReference = monitoringServiceServiceRegistration.getReference();

        retrievingServiceServiceRegistration = bundleContext.registerService(
                RetrievingService.class,
                new RetrievingServiceImpl(),
                new Hashtable<>()
        );

        retrievingServiceServiceReference = retrievingServiceServiceRegistration.getReference();
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        loggingServiceServiceRegistration.unregister();
        monitoringServiceServiceRegistration.unregister();
        retrievingServiceServiceRegistration.unregister();
    }
}
