package storageService;

import definition.event.LoggingEvent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;

public class StorageService {

    private static final String SEPARATOR = "/";
    private static final String EMPTY_STR = "";
    private static final String FILE_NAME = SEPARATOR + "Events.csv";

    public void storeEventsInCSV(List<LoggingEvent> events) {
        Path pathToFile = Paths.get(Paths.get(EMPTY_STR).toAbsolutePath() + FILE_NAME);
        try {
            if (Files.notExists(pathToFile)) {
                Files.createFile(pathToFile);
            }

            List<String> toStringEvents = events.stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());

            Files.write(pathToFile, toStringEvents, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
