package com.example.lab12;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWebTestClient
class Lab12ApplicationTests {
    @Autowired
    private WebTestClient client;

    @Test
    public void testNewUser() {
        client
                .post()
                .uri(uriBuilder -> uriBuilder
                        .pathSegment("user")
                        .queryParam("name", "testUser")
                        .queryParam("preferredCurrency", "EURO")
                        .build())
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(String.class)
                .isEqualTo("{\"id\":1,\"name\":\"testUser\",\"preferredCurrency\":\"EURO\"}");
    }

    @Test
    public void testNewItem() {
        client
                .post()
                .uri(uriBuilder -> uriBuilder
                        .pathSegment("item")
                        .queryParam("name", "testItem")
                        .queryParam("description", "testItemDesc")
                        .queryParam("dollarPrice", "24.0")
                        .build())
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(String.class)
                .isEqualTo("{\"id\":1,\"name\":\"testItem\",\"description\":\"testItemDesc\",\"dollarPrice\":24.0}");
    }

    @Test
    public void testAllItems() {
        client
                .get()
                .uri(uriBuilder -> uriBuilder
                        .pathSegment("item")
                        .queryParam("userId", "2003")
                        .build())
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(String.class)
                .isEqualTo("[{\"id\":1001,\"name\":\"item 1\",\"description\":\"item 1 description\",\"price\":41295.04},{\"id\":1002,\"name\":\"item 2\",\"description\":\"item 2 description\",\"price\":1213.8009},{\"id\":1003,\"name\":\"item 3\",\"description\":\"item 3 description\",\"price\":4901.508699999999}]");
    }
}
