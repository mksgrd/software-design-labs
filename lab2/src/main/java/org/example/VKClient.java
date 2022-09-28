package org.example;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class VKClient implements PostClient {
    private final String accessToken;
    private final String apiPrefix;

    public VKClient(String accessToken, String serverApiPrefix) {
        this.accessToken = accessToken;
        this.apiPrefix = serverApiPrefix;
    }

    public VKClient(String accessToken) {
        this(accessToken, "https://api.vk.com");
    }

    @Override
    public List<Post> searchPosts(String query, long startTime, long endTime) {
        URI requestUri = URI.create(apiPrefix + "/method/newsfeed.search?q=" + query
                + "&start_time=" + startTime
                + "&end_time=" + endTime
                + "&access_token=" + accessToken
                + "&v=5.131");

        HttpRequest request = HttpRequest.newBuilder(requestUri).GET().build();

        HttpClient client = HttpClient.newHttpClient();

        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error while sending request to server");
        }

        JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();

        JsonArray items = jsonResponse.getAsJsonObject("response").getAsJsonArray("items");

        return StreamSupport.stream(items.spliterator(), false)
                .map(JsonElement::getAsJsonObject)
                .map(json -> json.get("date"))
                .map(JsonElement::getAsLong)
                .map(Post::new)
                .collect(Collectors.toList());
    }
}
