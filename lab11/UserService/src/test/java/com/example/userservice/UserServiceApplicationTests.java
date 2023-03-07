package com.example.userservice;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Testcontainers
@AutoConfigureWebTestClient
class UserServiceApplicationTests {
    @Autowired
    private WebTestClient client;
    private WebTestClient.ResponseSpec responseSpec;

    @Container
    private final GenericContainer<?> tradeServiceServer = new FixedHostPortGenericContainer<>("trade-service:latest")
            .withFixedExposedPort(8081, 8081)
            .withExposedPorts(8081);

    private void sendPostRequestToTradeServer(String uri) {
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(uri))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        try {
            HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.discarding());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    public void initTradeService() {
        List.of(
                "http://localhost:8081/add-company?id=1&name=1&shares=200&price=3",
                "http://localhost:8081/add-trader?id=1&name=1&money=1000",
                "http://localhost:8081/buy-shares?traderId=1&compId=1&amount=30"
        ).forEach(this::sendPostRequestToTradeServer);
    }

    @Test
    public void testContainer() {
        Assertions.assertTrue(tradeServiceServer.isRunning());
    }

    @Test
    public void testNewUser() {
        client
                .post()
                .uri("/new-user?id=2&name=2&money=1000")
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Test
    public void testAddMoney() {
        client
                .post()
                .uri("/add-money?traderId=1&amount=500")
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Test
    public void testListShares() {
        client
                .get()
                .uri("/list-shares?traderId=1")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(String.class)
                .isEqualTo("Company: 1, Amount: 30, Price: 3");
    }

    @Test
    public void testSummary() {
        client
                .get()
                .uri("/summary?traderId=1")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(Integer.class)
                .isEqualTo(90);
    }

    @Test
    public void testBuyShares() {
        client
                .post()
                .uri("/buy-shares?traderId=1&compId=1&amount=6")
                .exchange()
                .expectStatus()
                .is2xxSuccessful();

        client
                .get()
                .uri("/list-shares?traderId=1")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(String.class)
                .isEqualTo("Company: 1, Amount: 36, Price: 3");
    }

    @Test
    public void testSellShares() {
        client
                .post()
                .uri("/sell-shares?traderId=1&compId=1&amount=6")
                .exchange()
                .expectStatus()
                .is2xxSuccessful();

        client
                .get()
                .uri("/list-shares?traderId=1")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(String.class)
                .isEqualTo("Company: 1, Amount: 24, Price: 3");
    }

    @Test
    public void testPriceChanged() {
        sendPostRequestToTradeServer("http://localhost:8081/change-price?compId=1&price=13");

        client
                .get()
                .uri("/list-shares?traderId=1")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(String.class)
                .isEqualTo("Company: 1, Amount: 30, Price: 13");
    }

}
