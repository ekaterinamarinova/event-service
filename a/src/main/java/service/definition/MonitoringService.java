package service.definition;

import listener.MonitoringListener;

public interface MonitoringService {
    void addMonitoringListener(MonitoringListener monitoringListener);
    void notifyListenerToMonitorEvents();
}
