package ru.practicum.ewm.statistic;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.practicum.statistic.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface HttpStatisticClient {

    HttpStatus addStatistic(String uri, String ip);

    ResponseEntity<List<ViewStats>> getStatistic(LocalDateTime start, LocalDateTime end, String[] uris, Boolean unique);
}
