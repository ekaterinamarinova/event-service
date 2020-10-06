package service.definition;

import listener.MonitoringListener;

public interface MonitoringService extends ServiceA {
    void addMonitoringListener(MonitoringListener monitoringListener);
}
