package ru.practicum.service;

import ru.practicum.model.Statistic;
import ru.practicum.statistic.EndpointHit;

import java.time.LocalDateTime;
import static ru.practicum.statistic.utils.DateTimeUtils.strToDateTime;

public class StatisticMapper {

    public static Statistic toStatistic(EndpointHit endpointHit) {
        LocalDateTime hitTime = endpointHit.getTimestamp() == null
                ? LocalDateTime.now()
                : strToDateTime(endpointHit.getTimestamp());
        return new Statistic(null,
                endpointHit.getApp(),
                endpointHit.getUri(),
                endpointHit.getIp(),
                hitTime);
    }
}