package service.definition;

import java.util.List;

public interface StorageService {
    void storeEventsInCSV(List<String> events);
}
