package ru.practicum.service;

import ru.practicum.statistic.EndpointHit;
import ru.practicum.statistic.ViewStats;

import java.util.List;

public interface StatisticService {

    void add(EndpointHit endpointHit);

    List<ViewStats> getStatistic(String start, String end, String[] uris, boolean unique);
}