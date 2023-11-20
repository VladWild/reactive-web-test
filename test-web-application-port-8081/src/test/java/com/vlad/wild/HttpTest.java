package com.vlad.wild;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
public class HttpTest {
    private static final RestTemplate restTemplate = new RestTemplate();

    private static final String WEB_FLUX_URL = "http://localhost:8080/test-webflux";
    private static final String MVC_URL = "http://localhost:8090/test-mvc";

    @Test
    void httpTest() {
        ResponseEntity<Long> exchange = restTemplate.exchange(
                "http://localhost:8080/test-webflux/5/5/5",
                HttpMethod.GET,
                new HttpEntity<>(new HttpHeaders()),
                Long.class
        );

        System.out.println(exchange.getBody());
    }

    @Test
    void httpTestCycle() {
        int count = 1000;
        AtomicInteger countEnd = new AtomicInteger();
        final long start = System.currentTimeMillis();

        AtomicInteger j = new AtomicInteger();
        for (int i = 0; i < count; i++) {
            new Thread(() -> {
                int number = j.incrementAndGet();
                System.out.println("Start - " + number);
                ResponseEntity<Long> exchange = restTemplate.exchange(
                        String.format("%s/%d/%d/%d/%d",
                                WEB_FLUX_URL,
                                //MVC_URL,
                                5, 5, 5, number),
                        HttpMethod.GET,
                        new HttpEntity<>(new HttpHeaders()),
                        Long.class
                );
                countEnd.incrementAndGet();
                System.out.println(countEnd.intValue() + ") " +
                        "End - " + number + ", " +
                        "Response: " + exchange.getBody() + ", " +
                        "Thread: " + Thread.currentThread().getName() + ", " +
                        "Time: " + (System.currentTimeMillis() - start) / 1000);
            }).start();
        }

        while (true) {
            if (count == countEnd.intValue()) {
                break;
            }
        }
    }
}
