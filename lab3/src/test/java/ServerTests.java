import org.junit.jupiter.api.*;
import ru.akirakozov.sd.refactoring.Main;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServerTests {

    @BeforeAll
    static void beforeAll() throws InterruptedException {
        new Thread(() -> {
            try {
                Main.main(null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();

        // Wait for server initialization
        Thread.sleep(1000);
    }

    private HttpClient client;

    @BeforeEach
    void setUp() {
        client = HttpClient.newHttpClient();
    }

    private HttpResponse<String> sendRequest(String uri) {
        URI requestUri = URI.create(uri);
        HttpRequest request = HttpRequest.newBuilder(requestUri).GET().build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    private void assertGetBody(String uri, String expected) {
        HttpResponse<String> response = sendRequest(uri);
        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals(expected, response.body());
    }

    @Test
    @Order(1)
    void testAddProduct() {
        Assertions.assertEquals(200, sendRequest("http://localhost:8081/add-product?name=iphone6&price=300").statusCode());
        Assertions.assertEquals(200, sendRequest("http://localhost:8081/add-product?name=kek&price=15").statusCode());
    }

    @Test
    @Order(2)
    void testGetProducts() {
        assertGetBody("http://localhost:8081/get-products",
                """
                        <html><body>
                        iphone6	300</br>
                        kek	15</br>
                        </body></html>
                        """);
    }


    @Test
    @Order(3)
    void testMaxQuery() {
        assertGetBody("http://localhost:8081/query?command=max",
                """
                        <html><body>
                        <h1>Product with max price: </h1>
                        iphone6	300</br>
                        </body></html>
                        """);
    }

    @Test
    @Order(4)
    void testMinQuery() {
        assertGetBody("http://localhost:8081/query?command=min",
                """
                        <html><body>
                        <h1>Product with min price: </h1>
                        kek	15</br>
                        </body></html>
                        """);
    }

    @Test
    @Order(5)
    void testSumQuery() {
        assertGetBody("http://localhost:8081/query?command=sum",
                """
                        <html><body>
                        Summary price:\s
                        315
                        </body></html>
                        """);
    }

    @Test
    @Order(6)
    void testCountQuery() {
        assertGetBody("http://localhost:8081/query?command=count",
                """
                        <html><body>
                        Number of products:\s
                        2
                        </body></html>
                        """);
    }
}
