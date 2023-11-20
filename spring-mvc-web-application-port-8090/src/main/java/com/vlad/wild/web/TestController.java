package com.vlad.wild.web;

import com.vlad.wild.rest.RestTestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test-mvc")
public class TestController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final RestTestService restTestService;

    public TestController(RestTestService restTestService) {
        this.restTestService = restTestService;
    }

    @GetMapping("/{delayStart}/{delayRest}/{delayEnd}/{iter}")
    public ResponseEntity<Void> delay(@PathVariable("delayStart") Long delayStart,
                                      @PathVariable("delayRest") Long delayRest,
                                      @PathVariable("delayEnd") Long delayEnd,
                                      @PathVariable("iter") Long iter) throws InterruptedException {
        sleep(delayStart, "Start", iter);
        restTestService.getTest(delayRest);
        sleep(delayEnd, "End", iter);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void sleep(long delay, String phase, Long iter) {
        long start = System.currentTimeMillis();
        logger.info("{} - iter, {} - start delay", iter, phase);
        int i = 0;
        long endTime = delay * 1000;
        while ((System.currentTimeMillis() - start) < endTime) {
            i++;
        }
        logger.info("{} - iter, {} - end delay {} ms, i = {}",
                iter, phase, System.currentTimeMillis() - start, i);
    }
}
