import org.example.EventsStatistic;
import org.example.EventsStatisticImpl;
import org.example.SettableClock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class EventsStatisticTest {
    private final String EVENT_NAME = "event";
    private SettableClock clock;
    private EventsStatistic eventsStatistic;

    @BeforeEach
    void setUp() {
        clock = new SettableClock();
        eventsStatistic = new EventsStatisticImpl(clock);
    }

    @Test
    public void noEvents() {
        Assertions.assertEquals(0, eventsStatistic.getEventStatisticByName(EVENT_NAME));
        Assertions.assertEquals(Collections.emptyMap(), eventsStatistic.getAllEventStatistic());
    }

    @Test
    public void oneEvent() {
        eventsStatistic.incEvent(EVENT_NAME);
        Assertions.assertEquals(1.0 / 60, eventsStatistic.getEventStatisticByName(EVENT_NAME));
        Assertions.assertEquals(Map.of(EVENT_NAME, 1.0 / 60), eventsStatistic.getAllEventStatistic());
    }

    @Test
    public void eventAfterHourExcluded() {
        eventsStatistic.incEvent(EVENT_NAME);
        clock.offset(Duration.ofHours(1));

        Assertions.assertEquals(1.0 / 60, eventsStatistic.getEventStatisticByName(EVENT_NAME));
        Assertions.assertEquals(Map.of(EVENT_NAME, 1.0 / 60), eventsStatistic.getAllEventStatistic());

        clock.offset(Duration.ofSeconds(1));

        Assertions.assertEquals(0, eventsStatistic.getEventStatisticByName(EVENT_NAME));
        Assertions.assertEquals(Map.of(EVENT_NAME, 0.0), eventsStatistic.getAllEventStatistic());
    }

    @Test
    public void recurringEvent() {
        for (int i = 0; i < 60 * 3; i++) {
            eventsStatistic.incEvent(EVENT_NAME);
            clock.offset(Duration.ofMinutes(1));
        }
        Assertions.assertEquals(1, eventsStatistic.getEventStatisticByName(EVENT_NAME));
        Assertions.assertEquals(Map.of(EVENT_NAME, 1.0), eventsStatistic.getAllEventStatistic());
    }

    @Test
    public void multipleEvents() {
        double expectedValue = 1.0 / 10;
        Map<String, Double> expectedMap = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            expectedMap.put(EVENT_NAME + i, expectedValue);
        }

        for (int i = 0; i < 10000; i++) {
            eventsStatistic.incEvent(EVENT_NAME + i % 10);
            clock.offset(Duration.ofMinutes(1));
        }

        Assertions.assertEquals(expectedMap, eventsStatistic.getAllEventStatistic());
        for (int i = 0; i < 10; i++) {
            Assertions.assertEquals(expectedValue, eventsStatistic.getEventStatisticByName(EVENT_NAME + i % 10));
        }
    }
}
