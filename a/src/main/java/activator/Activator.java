package activator;

import definition.event.LoggingEvent;
import definition.service.LoggingService;
import definition.service.MonitoringService;
import definition.service.RetrievingService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import service.LoggingServiceImpl;
import service.MonitoringServiceImpl;
import service.RetrievingServiceImpl;

import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Activator implements BundleActivator {

    private ServiceReference<LoggingService> loggingServiceServiceReference;
    private ServiceRegistration<LoggingService> loggingServiceServiceRegistration;

    private ServiceReference<MonitoringService> monitoringServiceServiceReference;
    private ServiceRegistration<MonitoringService> monitoringServiceServiceRegistration;

    private ServiceReference<RetrievingService> retrievingServiceServiceReference;
    private ServiceRegistration<RetrievingService> retrievingServiceServiceRegistration;

    @Override
    public void start(BundleContext bundleContext) throws Exception {

        final List<LoggingEvent> loggingEventList = new CopyOnWriteArrayList<>();

        RetrievingService retrievingService = new RetrievingServiceImpl(loggingEventList);
        MonitoringServiceImpl monitoringService = new MonitoringServiceImpl(loggingEventList);
        LoggingService loggingService = new LoggingServiceImpl(loggingEventList, monitoringService);

        // OSGi Service Registration
        loggingServiceServiceRegistration = bundleContext.registerService(
                LoggingService.class,
                loggingService,
                new Hashtable<>()
        );
        loggingServiceServiceReference = loggingServiceServiceRegistration.getReference();

        retrievingServiceServiceRegistration = bundleContext.registerService(
                RetrievingService.class,
                retrievingService,
                new Hashtable<>()
        );
        retrievingServiceServiceReference = retrievingServiceServiceRegistration.getReference();

        monitoringServiceServiceRegistration = bundleContext.registerService(
                MonitoringService.class,
                monitoringService,
                new Hashtable<>()
        );
        monitoringServiceServiceReference = monitoringServiceServiceRegistration.getReference();
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        loggingServiceServiceRegistration.unregister();
        monitoringServiceServiceRegistration.unregister();
        retrievingServiceServiceRegistration.unregister();
    }
}
