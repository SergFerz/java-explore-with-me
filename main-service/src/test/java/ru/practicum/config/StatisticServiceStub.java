package ru.practicum.config;

import ru.practicum.ewm.statistic.StatisticService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class StatisticServiceStub implements StatisticService {

    @Override
    public Map<Long, Long> getEventViewCount(Set<Long> eventIds) {
        return eventIds.stream()
                .collect(Collectors.toMap(l ->  l, l -> 0L));
    }

    @Override
    public Long getEventViewCount(Long eventId) {
        return 0L;
    }

    @Override
    public void addStatistic(HttpServletRequest httpRequest) {

    }
}
