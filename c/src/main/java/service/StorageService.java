package service;

import definition.event.LoggingEvent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class StorageService {

    private static final String SEPARATOR = "/";
    private static final String EMPTY_STR = "";
    private static final String FILE_NAME = SEPARATOR + "Events.csv";

    public void storeEventsInCSV(List<LoggingEvent> events) {
        String pathToFile = Paths.get(EMPTY_STR).toAbsolutePath().toString() + FILE_NAME;

        try {
            if (Files.notExists(Paths.get(pathToFile))) {
                Files.createFile(Paths.get(pathToFile));
            }

            for (LoggingEvent event : events) {
                Files.write(Paths.get(pathToFile), event.toString().getBytes(), StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
