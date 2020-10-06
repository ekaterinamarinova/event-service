package service.implementation;

import service.definition.LoggingService;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggingServiceImpl implements LoggingService {

    private static final Logger LOGGER = Logger.getLogger(LoggingServiceImpl.class.getName());

    @Override
    public void logEvent(String eventType, String msg) {
        LOGGER.log(Level.INFO, msg, eventType);
    }

}