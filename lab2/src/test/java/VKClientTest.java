import org.example.Post;
import org.example.PostClient;
import org.example.Secrets;
import org.example.VKClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class VKClientTest {
    private MockServerClient stubServer;

    private static final String STUB_RESPONSE = "{\"response\":{\"items\":[{\"date\":1024}]}}";

    @BeforeEach
    void setUp() {
        stubServer = ClientAndServer.startClientAndServer(8080);

        stubServer
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/method/newsfeed.search")
                )
                .respond(
                        response()
                                .withBody(STUB_RESPONSE)
                );
    }

    @AfterEach
    void tearDown() {
        stubServer.stop();
    }

    @Test
    public void testOnReal() {
        PostClient client = new VKClient(Secrets.ACCESS_TOKEN);
        assertEquals(
                List.of(new Post(1664340628),
                        new Post(1664298095),
                        new Post(1664291136),
                        new Post(1664282707),
                        new Post(1664278860)),
                client.searchPosts("кринж", 1664277378, 1664363778)
        );
    }

    @Test
    public void testOnStub() {
        PostClient client = new VKClient("Token_is_not_used_here", "http://localhost:8080");

        List<Post> posts = client.searchPosts("query", 0, 1025);

        assertEquals(List.of(new Post(1024)), posts);
    }
}
