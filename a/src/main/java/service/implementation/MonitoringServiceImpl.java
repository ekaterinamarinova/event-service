package service.implementation;

import listener.definition.MonitoringListener;
import service.definition.MonitoringService;

/**
 * Show real-time events flow
 */
public class MonitoringServiceImpl implements MonitoringService {

    private MonitoringListener monitoringListener;

    @Override
    public void addMonitoringListener(MonitoringListener monitoringListener) {
        this.monitoringListener = monitoringListener;
    }

    @Override
    public void notifyListenerToMonitorEvents() {
        monitoringListener.monitorEvents();
    }
}
