package service.implementation;

import event.definition.Event;
import service.definition.LoggingService;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class LoggingServiceImpl implements LoggingService {

    private static final Logger LOGGER = Logger.getLogger(LoggingServiceImpl.class.getName());
    private List<Event> eventList = new ArrayList<>();

    public void logEvent(Event event) {
        LOGGER.info(event.getEventType().getType() + " : " + event.getEventType().getMessage());
        eventList.add(event);
    }

    @Override
    public List<Event> getEvents() {
        return eventList;
    }
}