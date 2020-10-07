package service.implementation;

import service.definition.StorageService;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class StorageServiceImpl implements StorageService {

    private static final String FILE_NAME = "Events.csv";
    private static final String SEPARATOR = "/";

    @Override
    public void storeEventsInCSV(List<String> events) {
        String pathToFile = SEPARATOR + "resources" + SEPARATOR + FILE_NAME;

        try(FileWriter fileWriter = new FileWriter(new File(pathToFile))){
            for (String e:events) {
                fileWriter.write(e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
