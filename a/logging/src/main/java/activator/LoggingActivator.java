package activator;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import service.definition.LoggingService;
import service.implementation.LoggingServiceImpl;

import java.util.Hashtable;

public class LoggingActivator implements BundleActivator {

    private ServiceReference<LoggingService> reference;
    private ServiceRegistration<LoggingService> registration;

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        registration = bundleContext.registerService(
                LoggingService.class,
                new LoggingServiceImpl(),
                new Hashtable<>()
        );
        reference = registration.getReference();
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        registration.unregister();
    }
}
