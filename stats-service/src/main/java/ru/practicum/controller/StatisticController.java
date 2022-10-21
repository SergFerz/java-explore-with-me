package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.service.StatisticService;
import ru.practicum.statistic.EndpointHit;
import ru.practicum.statistic.ViewStats;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class StatisticController {

    private final StatisticService statisticService;

    @PostMapping(value = "/hit")
    public void add(@Valid @RequestBody EndpointHit endpointHit) {
        statisticService.add(endpointHit);
    }

    @GetMapping(value = "/stats")
    public List<ViewStats> getStatistic(
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam String[] uris,
            @RequestParam(required = false, defaultValue = "false") boolean unique) {
        return statisticService.getStatistic(start, end, uris, unique);
    }
}