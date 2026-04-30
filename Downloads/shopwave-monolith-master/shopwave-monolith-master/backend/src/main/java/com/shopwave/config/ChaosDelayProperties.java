package com.shopwave.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "shopwave.chaos.delay")
public class ChaosDelayProperties {

    private boolean enabled = false;
    private long fixedMs = 0;
    private long jitterMs = 0;
    private List<String> targetOperations = List.of();
}