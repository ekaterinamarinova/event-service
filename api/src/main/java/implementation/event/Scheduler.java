package implementation.event;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class Scheduler {
    public void scheduleEventExecution(Runnable runnable, int initialDelay, int period, TimeUnit timeUnit) {
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(2);
        scheduledExecutorService.scheduleAtFixedRate(runnable, initialDelay , period, timeUnit);
    }
}
