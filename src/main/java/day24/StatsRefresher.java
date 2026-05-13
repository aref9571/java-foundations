package day24;

import day05.JobApplication;
import day10.ApplicationRepository;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.jar.JarEntry;

public class StatsRefresher {
    public record StatsSnapshot(long activeCount , long finalCount){}

    private final ApplicationRepository repository;
    private final ScheduledExecutorService scheduler;
    private volatile StatsSnapshot currentSnapshot;

    public StatsRefresher(ApplicationRepository repository){
        this.repository = Objects.requireNonNull(repository , "repository must not be null");
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
        this.currentSnapshot = new StatsSnapshot(0,0);
    }

    public void start(long initialDelaySeconds , long periodSeconds){
        scheduler.scheduleAtFixedRate(this::refreshStats , initialDelaySeconds , periodSeconds , TimeUnit.SECONDS);
    }

    private void refreshStats(){
        List<JobApplication> apps = repository.findAll();
        long active = apps.stream().filter(JobApplication::isActive).count();
        long fin = apps.stream().filter(app -> !app.isActive()).count();
        currentSnapshot = new StatsSnapshot(active,fin);
    }

    public StatsSnapshot getCurrentSnapshot(){
        return currentSnapshot;
    }

    public void forceRefresh(){
        refreshStats();
    }

    public void shutdown(){
        scheduler.shutdown();
    }
}
