package com.shopwave.service;

import com.shopwave.config.ChaosDelayProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChaosDelayService {

    private final ChaosDelayProperties properties;

    public void applyDelay(String operationName) {
        if (!properties.isEnabled()) {
            return;
        }

        if (!properties.getTargetOperations().contains(operationName)) {
            return;
        }

        long jitter = 0;

        if (properties.getJitterMs() > 0) {
            jitter = ThreadLocalRandom.current().nextLong(0, properties.getJitterMs() + 1);
        }

        long totalDelay = properties.getFixedMs() + jitter;

        log.info(
                "Chaos delay applied. operation={}, fixedMs={}, jitterMs={}, totalDelayMs={}",
                operationName,
                properties.getFixedMs(),
                jitter,
                totalDelay
        );

        try {
            Thread.sleep(totalDelay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Chaos delay interrupted", e);
        }
    }
}