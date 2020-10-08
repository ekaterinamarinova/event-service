package listener.definition;

import event.definition.LoggingEvent;

import java.util.EventListener;

public interface MonitoringListener extends EventListener {
    void eventReceived(LoggingEvent event);
}
