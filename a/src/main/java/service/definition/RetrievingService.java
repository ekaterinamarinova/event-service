package service.definition;

import java.time.LocalTime;
import java.util.List;

public interface RetrievingService extends ServiceA {
    List<String> retrieve(String eventType, LocalTime startTime, LocalTime endTime);
}
