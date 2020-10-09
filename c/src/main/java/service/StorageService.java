package service;

import definition.event.LoggingEvent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;

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
            List<String> toStringEvents = events.stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());

            Files.write(Paths.get(pathToFile), toStringEvents,StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
