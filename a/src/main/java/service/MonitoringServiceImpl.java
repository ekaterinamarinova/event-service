package service;


import definition.event.LoggingEvent;
import definition.listener.MonitoringListener;
import definition.service.MonitoringService;
import org.osgi.service.component.annotations.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Show real-time events flow
 */
@Component(service = MonitoringService.class)
public class MonitoringServiceImpl implements MonitoringService {

    private final List<MonitoringListener> monitoringListeners;

    public MonitoringServiceImpl() {
        monitoringListeners = new CopyOnWriteArrayList<>();
    }

    @Override
    public void addMonitoringListener(MonitoringListener monitoringListener) {
        monitoringListeners.add(monitoringListener);
    }

    public void notifyMonitoringListeners(LoggingEvent event) {
        for (MonitoringListener m : monitoringListeners) {
            m.eventReceived(event);
        }
    }
}
