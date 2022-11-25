package ru.practicum.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.practicum.ewm.statistic.StatisticService;


@Configuration
public class TestConfig {

    @Bean
    public StatisticService statisticService() {
        return new StatisticServiceStub();
    }
}