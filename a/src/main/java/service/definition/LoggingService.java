package service.definition;

public interface LoggingService extends ServiceA {
    void logEvent(String eventType, String s);
}