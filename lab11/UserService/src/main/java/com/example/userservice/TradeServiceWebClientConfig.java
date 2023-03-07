package com.example.userservice;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class TradeServiceWebClientConfig {

    private final TradeServiceProperties properties;

    @Bean
    public WebClient tradeServiceWebClient() {
        return WebClient.create(properties.getUrl());
    }
}
