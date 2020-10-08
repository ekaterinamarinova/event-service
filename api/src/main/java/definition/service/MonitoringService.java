package definition.service;


import definition.listener.MonitoringListener;

public interface MonitoringService {
    void addMonitoringListener(MonitoringListener monitoringListener);
}
