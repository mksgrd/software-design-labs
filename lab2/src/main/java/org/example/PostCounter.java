package org.example;

import java.time.Duration;
import java.time.Instant;

public class PostCounter {
    private final PostClient client;

    public PostCounter(PostClient client) {
        this.client = client;
    }

    public int[] countPosts(String query, Instant fromMoment, int hoursPeriod) {
        if (hoursPeriod < 1 || hoursPeriod > 24) {
            throw new IllegalArgumentException("Hours period should be within bounds from 1 to 24");
        }

        long startTime = fromMoment.minus(Duration.ofHours(hoursPeriod)).getEpochSecond();
        long endTime = fromMoment.getEpochSecond();

        int[] result = new int[hoursPeriod];

        client.searchPosts(query, startTime, endTime)
                .forEach(post -> result[(int) ((post.date() - startTime) * hoursPeriod / (endTime - startTime))]++);

        return result;
    }
}
