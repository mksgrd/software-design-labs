package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LRUCacheTest {

    private LRUCache<Integer, Integer> cache;
    public static final int CAPACITY = 4;

    @BeforeEach
    void setUp() {
        cache = new LRUCache<>(CAPACITY);
    }

    @Test
    void testNonPositiveCapacity() {
        assertThrows(AssertionError.class, () -> new LRUCache<>(0));

        assertThrows(AssertionError.class, () -> new LRUCache<>(-12));
    }

    @Test
    void testNonExistingGet() {
        assertNull(cache.get(51));
    }

    @Test
    void testPutAndGetIdentity() {
        Integer value = 3;
        cache.put(1, value);
        assertEquals(value, cache.get(1));
    }

    @Test
    void testNoEviction() {
        cache.put(1, 1);
        cache.put(2, 2);
        cache.put(3, 3);
        cache.put(4, 4);
        for (int i = 0; i < 2 * CAPACITY; i++) {
            assertEquals(1, cache.get(1));
            assertEquals(2, cache.get(2));
            assertEquals(4, cache.get(4));
            assertEquals(3, cache.get(3));
        }
    }

    @Test
    void testEviction() {
        cache.put(1, 1);
        cache.put(2, 2);
        cache.put(3, 3);
        cache.put(4, 4);

        cache.put(5, 5);
        assertNull(cache.get(1));
    }

    @Test
    void testCacheNotAcceptNullKeys() {
        assertThrows(AssertionError.class, () -> cache.put(null, 1));
    }

    @Test
    void testCacheNotAcceptNullValues() {
        assertThrows(AssertionError.class, () -> cache.put(1, null));
    }
}