package org.example;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

public class SettableClock extends Clock {
    private Clock clock = fixed(Instant.now(), systemUTC().getZone());

    @Override
    public ZoneId getZone() {
        return clock.getZone();
    }

    @Override
    public Clock withZone(ZoneId zone) {
        return clock.withZone(zone);
    }

    @Override
    public Instant instant() {
        return clock.instant();
    }

    public void offset(Duration duration) {
        clock = offset(clock, duration);
    }
}
