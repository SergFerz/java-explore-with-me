package ru.practicum.ewm.event.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.model.EventState;
import ru.practicum.ewm.event.service.EventService;
import ru.practicum.ewm.statistic.StatisticService;
import ru.practicum.ewm.utils.DateTimeUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/events")
@Validated
@AllArgsConstructor
@Slf4j
public class EventControllerPublic {

    private final EventService eventService;
    private final StatisticService statisticService;

    @GetMapping
    public List<EventShortDto> getEventsPublicByParams(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) Long[] categories,
            @RequestParam(required = false) String rangeStart,
            @RequestParam(required = false) String rangeEnd,
            @RequestParam(required = false) Boolean paid,
            @RequestParam(required = false) Boolean onlyAvailable,
            @RequestParam(required = false) EventSort sort,
            @RequestParam(required = false, defaultValue = "0")@PositiveOrZero Integer from,
            @RequestParam(required = false, defaultValue = "10")@Positive Integer size,
            HttpServletRequest request) {
        statisticService.addStatistic(request);
        EventParameters parameters = EventParameters.builder()
                .text(text)
                .categories(categories)
                .rangeStart(DateTimeUtils.strIso8601toDateTime(rangeStart))
                .rangeEnd(DateTimeUtils.strIso8601toDateTime(rangeEnd))
                .paid(paid)
                .onlyAvailable(onlyAvailable)
                .build();
        log.info("Get events with parameters");
        return eventService.getEventsPublicByParams(parameters, sort, from, size);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEventById(@PathVariable @Positive Long eventId,
                                     HttpServletRequest request) {
        log.info("Get event by id = {}", eventId);
        statisticService.addStatistic(request);
        return eventService.getEventById(eventId);
    }
}
