import org.example.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PostCounterTest {
    @Mock
    private PostClient mockClient;


    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this).close();
    }

    @Test()
    public void testHoursRanges() {
        assertThrows(IllegalArgumentException.class, () -> new PostCounter(mockClient).countPosts("query", Instant.now(), 25));
        assertThrows(IllegalArgumentException.class, () -> new PostCounter(mockClient).countPosts("query", Instant.now(), 0));
        assertDoesNotThrow(() -> new PostCounter(mockClient).countPosts("query", Instant.now(), 12));
    }

    @Test
    public void testCountPostsOnMock() {
        PostCounter postCounter = new PostCounter(mockClient);

        String query = "аниме";
        int hoursPeriod = 3;
        Instant currentMoment = Instant.ofEpochSecond(1664306491);
        long endTime = currentMoment.getEpochSecond();
        long startTime = currentMoment.minus(Duration.ofHours(hoursPeriod)).getEpochSecond();
        List<Post> posts = List.of(
                new Post(1664301300),
                new Post(1664298095),
                new Post(1664298001)
        );

        Mockito.when(mockClient.searchPosts(query, startTime, endTime)).thenReturn(posts);
        Assertions.assertArrayEquals(new int[]{2, 1, 0}, postCounter.countPosts(query, currentMoment, hoursPeriod));
    }

    @Test
    public void testCountPostsOnReal() {
        PostCounter postCounter = new PostCounter(new VKClient(Secrets.ACCESS_TOKEN));
        Assertions.assertArrayEquals(
                new int[]{2, 1, 1},
                postCounter.countPosts("Путин", Instant.ofEpochSecond(1664308378), 3));
    }
}
