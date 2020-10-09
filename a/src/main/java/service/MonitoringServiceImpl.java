package service;


import definition.event.LoggingEvent;
import definition.listener.MonitoringListener;
import definition.service.MonitoringService;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Show real-time events flow
 */
public class MonitoringServiceImpl implements MonitoringService {

    private final List<MonitoringListener> monitoringListeners;
    private final List<LoggingEvent> loggingEvents;

    public MonitoringServiceImpl(List<LoggingEvent> loggingEvents) {
        monitoringListeners = new CopyOnWriteArrayList<>();
        this.loggingEvents = loggingEvents;
    }

    @Override
    public void addMonitoringListener(MonitoringListener monitoringListener) {
        monitoringListeners.add(monitoringListener);
    }

    public void notifyMonitoringListeners() {
        for (MonitoringListener m : monitoringListeners) {
            for (LoggingEvent event : loggingEvents) {
                m.eventReceived(event);
            }
        }
    }
}
