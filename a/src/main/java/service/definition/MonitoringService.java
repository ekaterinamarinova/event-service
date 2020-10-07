package service.definition;

import listener.definition.MonitoringListener;

public interface MonitoringService {
    void addMonitoringListener(MonitoringListener monitoringListener);
    void notifyListenerToMonitorEvents();
}
