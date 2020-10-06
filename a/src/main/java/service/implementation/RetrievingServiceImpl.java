package service.implementation;

import service.definition.RetrievingService;

import java.time.LocalTime;
import java.util.List;

public class RetrievingServiceImpl implements RetrievingService {
    @Override
    public List<String> retrieve(String eventType, LocalTime startTime, LocalTime endTime) {
        return null;
    }
}
