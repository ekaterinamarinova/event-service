package definition.listener;


import definition.event.LoggingEvent;

import java.util.EventListener;

public interface MonitoringListener extends EventListener {
    void eventReceived(LoggingEvent event);
}
