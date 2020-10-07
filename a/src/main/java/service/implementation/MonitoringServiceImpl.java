package service.implementation;

import listener.MonitoringListener;
import service.definition.MonitoringService;

import java.util.ArrayList;
import java.util.List;

/**
 * Show real-time events flow
 */
public class MonitoringServiceImpl implements MonitoringService {

    private List<MonitoringListener> listeners = new ArrayList<>();

    @Override
    public void addMonitoringListener(MonitoringListener monitoringListener) {
        listeners.add(monitoringListener);
    }

    public void notifyListenerToMonitorEvents() {
        for (MonitoringListener m : listeners) {
            m.monitorEvents();
        }
    }
}
