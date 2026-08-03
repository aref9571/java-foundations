package day26;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AnalyticsRefresher {
    private static final SimpleLogger log = new ConsoleLogger(AnalyticsRefresher.class);
    private final AnalyticsService analyticsService;
    private final ScheduledExecutorService scheduler;
    private volatile AnalyticsSnapshot currentSnapshot;

    public AnalyticsRefresher(AnalyticsService analyticsService){
        this.analyticsService = Objects.requireNonNull(analyticsService , "analyticsService must not be null");
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
        this.currentSnapshot = new AnalyticsSnapshot(0,0,0, Map.of(), List.of());

    }

    public void start(long initialDelaySeconds, long periodSeconds) {
        scheduler.scheduleAtFixedRate(this::safeRefresh,
                initialDelaySeconds,
                periodSeconds,
                TimeUnit.SECONDS);
    }

    private void safeRefresh(){
        try {
            AnalyticsSnapshot snapshot = analyticsService.computeSnapShot();
            currentSnapshot = snapshot;
            log.info("analytics snapshot refreshed: toal={} actice={} final={}",
                    snapshot.totalApplications() , snapshot.activeApplications() , snapshot.finalApplications());
        } catch (Exception e){
            log.error("Failed to refresh analytics snapshot" , e);
        }
    }
    public AnalyticsSnapshot getCurrentSnapshot() {
        return currentSnapshot;
    }

    public void shutdown() {
        scheduler.shutdown();
    }
}
