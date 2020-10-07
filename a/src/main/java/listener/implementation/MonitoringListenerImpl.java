package listener.implementation;

import listener.definition.MonitoringListener;
import service.definition.LoggingService;
import service.definition.MonitoringService;
import service.implementation.MonitoringServiceImpl;

import java.util.logging.Logger;

public class MonitoringListenerImpl implements MonitoringListener {

    private static final Logger LOGGER = Logger.getLogger(MonitoringListenerImpl.class.getName());

    private final LoggingService loggingService;

    public MonitoringListenerImpl(LoggingService loggingService) {
        this.loggingService = loggingService;
        MonitoringService monitoringService = new MonitoringServiceImpl();
        monitoringService.addMonitoringListener(this);
    }

    public void monitorEvents() {
        final int size = loggingService.getEvents().size();

        new Thread(() -> {
            while (true) {
                try {
                    wait(100);

                    if (size < loggingService.getEvents().size()) {
                        LOGGER.info(loggingService.getEvents().get(size - 1).getEventType().getType());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
