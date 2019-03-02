package com.edison.saas.bootstrap.monitor.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class CustomHealth implements HealthIndicator {
    @Override
    public Health health() {
        try {
//            RestTemplate restTemplate = new RestTemplate();
//            restTemplate.getForObject("http://localhost:8080/index", String.class);
            return Health.up().build();
        } catch (Exception e) {
            return Health.down().withDetail("down的原因：", e.getMessage()).build();
        }
    }
}