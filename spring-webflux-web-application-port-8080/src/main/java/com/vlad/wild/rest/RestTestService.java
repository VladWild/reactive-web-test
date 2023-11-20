package com.vlad.wild.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class RestTestService {
    private static final String TEST_URL = "http://localhost:8081/test/";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final WebClient webClient = WebClient.builder().build();

    public Mono<Long> getTest(long delay) {
        return webClient.get()
                .uri(TEST_URL + delay)
                .retrieve()
                .bodyToMono(Long.class)
                .doOnSuccess(id -> logger.info("Id response: {}", id))
                .doOnError(ex -> logger.info("Error: {}", ex.getMessage()));
    }
}
