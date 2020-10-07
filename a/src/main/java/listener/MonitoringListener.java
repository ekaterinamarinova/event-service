package listener;

import service.definition.MonitoringService;
import service.implementation.MonitoringServiceImpl;

import java.util.EventListener;

public class MonitoringListener implements EventListener {

    private MonitoringService monitoringService;

    public MonitoringListener() {
        this.monitoringService = new MonitoringServiceImpl();
        monitoringService.addMonitoringListener(this);
    }

    public void monitorEvents() {
        //TODO: TBI
    }

}
