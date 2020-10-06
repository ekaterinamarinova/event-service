package activator;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import service.definition.ServiceA;
import service.implementation.LoggingServiceImpl;
import service.implementation.MonitoringServiceImpl;
import service.implementation.RetrievingServiceImpl;

import java.util.Hashtable;

public class Activator implements BundleActivator {

    private ServiceReference<ServiceA> reference;
    private ServiceRegistration<ServiceA> registration;

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        registration = bundleContext.registerService(
                ServiceA.class,
                new LoggingServiceImpl(),
                new Hashtable<>()
        );

        registration = bundleContext.registerService(
                ServiceA.class,
                new MonitoringServiceImpl(),
                new Hashtable<>()
        );

        registration = bundleContext.registerService(
                ServiceA.class,
                new RetrievingServiceImpl(),
                new Hashtable<>()
        );

        reference = registration.getReference();
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        registration.unregister();
    }
}
