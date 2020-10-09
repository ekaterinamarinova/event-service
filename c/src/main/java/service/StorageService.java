package service;

import definition.event.LoggingEvent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class StorageService {

    private static final String FILE_NAME = "Events.csv";
    private static final String SEPARATOR = "/";

    public synchronized void storeEventsInCSV(List<LoggingEvent> events) {
        String pathToFile = SEPARATOR + "resources" + SEPARATOR + FILE_NAME;

        try(FileWriter fileWriter = new FileWriter(new File(pathToFile))){
            for (LoggingEvent e:events) {
                fileWriter.write(String.valueOf(e));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
