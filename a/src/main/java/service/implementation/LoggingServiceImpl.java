package service.implementation;

import event.definition.Event;
import service.definition.LoggingService;

import java.util.ArrayList;
import java.util.List;

public class LoggingServiceImpl implements LoggingService {

    private final List<Event> eventList = new ArrayList<>();

    @Override
    public void logEvent(Event event) {
        eventList.add(event);
    }

    @Override
    public List<Event> getEvents() {
        return eventList;
    }

}