package org.example;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class EventsStatisticImpl implements EventsStatistic {

    private final Duration EVENT_LIFETIME = Duration.ofHours(1);

    private Clock clock;
    private Map<String, List<Instant>> statistics = new HashMap<>();

    public EventsStatisticImpl(Clock clock) {
        this.clock = clock;
    }

    @Override
    public void incEvent(String name) {
        statistics.computeIfAbsent(name, key -> new ArrayList<>());
        statistics.get(name).add(clock.instant());
    }

    @Override
    public double getEventStatisticByName(String name) {
        Instant now = clock.instant();
        return statistics
                .getOrDefault(name, Collections.emptyList())
                .stream()
                .filter(instant -> now.minus(EVENT_LIFETIME).compareTo(instant) <= 0 && instant.compareTo(now) <= 0)
                .count() / 60.0;
    }

    @Override
    public Map<String, Double> getAllEventStatistic() {
        Map<String, Double> result = new HashMap<>();
        for (String key : statistics.keySet()) {
            result.put(key, getEventStatisticByName(key));
        }
        return result;
    }

    @Override
    public void printStatistic() {
        System.out.println("Statistics:");
        for (var entry : getAllEventStatistic().entrySet()) {
            System.out.printf("Event %s, rpm %f", entry.getKey(), entry.getValue());
        }
    }
}
