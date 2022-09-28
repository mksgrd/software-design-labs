package org.example;

import java.util.List;

public interface PostClient {
    List<Post> searchPosts(String query, long startTime, long endTime);
}
