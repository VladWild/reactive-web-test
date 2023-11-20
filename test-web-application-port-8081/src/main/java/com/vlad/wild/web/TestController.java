package com.vlad.wild.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping(value = "/test")
public class TestController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/{delay}")
    public Long delay(@PathVariable("delay") Long delaySec) throws InterruptedException {
        long start = System.currentTimeMillis();

        logger.info("Start delay");
        Thread.sleep(delaySec * 1000);
        logger.info("End delay {} ms", System.currentTimeMillis() - start);

        return (long) (new Random().nextInt(100) + 1);
    }
}
