package activator;

import definition.event.LoggingEvent;
import definition.service.LoggingService;
import definition.service.MonitoringService;
import definition.service.RetrievingService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import service.LoggingServiceImpl;
import service.MonitoringServiceImpl;
import service.RetrievingServiceImpl;

import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Activator implements BundleActivator {

    private ServiceRegistration<LoggingService> loggingServiceServiceRegistration;
    private ServiceRegistration<MonitoringService> monitoringServiceServiceRegistration;
    private ServiceRegistration<RetrievingService> retrievingServiceServiceRegistration;

    @Override
    public void start(BundleContext bundleContext) {
        List<LoggingEvent> loggingEventList = new CopyOnWriteArrayList<>();

        RetrievingService retrievingService = new RetrievingServiceImpl(loggingEventList);
        MonitoringServiceImpl monitoringService = new MonitoringServiceImpl();
        LoggingService loggingService = new LoggingServiceImpl(loggingEventList, monitoringService);

        // OSGi Service Registration
        loggingServiceServiceRegistration = bundleContext.registerService(
                LoggingService.class,
                loggingService,
                new Hashtable<>()
        );

        retrievingServiceServiceRegistration = bundleContext.registerService(
                RetrievingService.class,
                retrievingService,
                new Hashtable<>()
        );

        monitoringServiceServiceRegistration = bundleContext.registerService(
                MonitoringService.class,
                monitoringService,
                new Hashtable<>()
        );
    }

    @Override
    public void stop(BundleContext bundleContext) {
        loggingServiceServiceRegistration.unregister();
        monitoringServiceServiceRegistration.unregister();
        retrievingServiceServiceRegistration.unregister();
    }
}
