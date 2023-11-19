package com.vlad.wild.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTestService {
    private static final String TEST_URL = "http://localhost:8081/test/";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void getTest(long delay) {
        long start = System.currentTimeMillis();
        logger.info("Start rest");

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response =
                restTemplate.getForEntity(TEST_URL + "/" + delay, String.class);
        logger.info("Rest response status {}", response.getStatusCode());

        logger.info("End rest {} ms", System.currentTimeMillis() - start);
    }
}
