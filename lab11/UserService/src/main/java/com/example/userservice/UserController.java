package com.example.userservice;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final WebClient tradeServiceWebClient;

    @PostMapping("/new-user")
    public void newUser(@RequestParam int id, @RequestParam String name, @RequestParam int money) {
        tradeServiceWebClient
                .post()
                .uri(uriBuilder -> uriBuilder
                        .path("/add-trader")
                        .queryParam("id", id)
                        .queryParam("name", name)
                        .queryParam("money", money)
                        .build())
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    @PostMapping("/add-money")
    public void addMoney(@RequestParam int traderId, @RequestParam int amount) {
        tradeServiceWebClient
                .post()
                .uri(uriBuilder -> uriBuilder
                        .path("/add-trader-money")
                        .queryParam("traderId", traderId)
                        .queryParam("amount", amount)
                        .build())
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    private Map<Integer, Integer> requestShares(int id) {
        return tradeServiceWebClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/list-shares")
                        .queryParam("traderId", id)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<Integer, Integer>>() {
                })
                .block();
    }

    private int requestPrice(int compId) {
        return tradeServiceWebClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/get-price")
                        .queryParam("compId", compId)
                        .build())
                .retrieve()
                .bodyToMono(Integer.class)
                .block();
    }

    @GetMapping("/list-shares")
    public String listShares(@RequestParam int traderId) {
        Map<Integer, Integer> shares = requestShares(traderId);

        StringBuilder sb = new StringBuilder();
        for (var entry : shares.entrySet()) {
            int amount = entry.getValue();
            int company = entry.getKey();

            sb.append("Company: ").append(company)
                    .append(", Amount: ").append(amount)
                    .append(", Price: ").append(requestPrice(company));
        }

        return sb.toString();
    }

    @GetMapping("/summary")
    public int summary(@RequestParam int traderId) {
        Map<Integer, Integer> shares = requestShares(traderId);
        int total = 0;
        for (var entry : shares.entrySet()) {
            total += entry.getValue() * requestPrice(entry.getKey());
        }
        return total;
    }

    @PostMapping("/buy-shares")
    public void buyShares(@RequestParam int traderId,
                          @RequestParam int compId,
                          @RequestParam int amount) {
        tradeServiceWebClient
                .post()
                .uri(uriBuilder -> uriBuilder
                        .path("/buy-shares")
                        .queryParam("traderId", traderId)
                        .queryParam("compId", compId)
                        .queryParam("amount", amount)
                        .build())
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    @PostMapping("/sell-shares")
    public void sellShares(@RequestParam int traderId,
                           @RequestParam int compId,
                           @RequestParam int amount) {
        tradeServiceWebClient
                .post()
                .uri(uriBuilder -> uriBuilder
                        .path("/sell-shares")
                        .queryParam("traderId", traderId)
                        .queryParam("compId", compId)
                        .queryParam("amount", amount)
                        .build())
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
