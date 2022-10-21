package ru.practicum.ewm.event.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.AdminUpdateEventRequest;
import ru.practicum.ewm.event.model.EventState;
import ru.practicum.ewm.event.service.EventService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/admin/events")
@Validated
@AllArgsConstructor
@Slf4j
public class EventControllerAdmin {
    private final EventService eventService;

    @GetMapping
    public List<EventFullDto> getEventsByParams(@RequestParam(required = false) Long[] users,
                                        @RequestParam(required = false) EventState[] states,
                                        @RequestParam(required = false) Long[] categories,
                                        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                    LocalDateTime rangeStart,
                                        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                    LocalDateTime rangeEnd,
                                        @RequestParam(required = false, defaultValue = "0")@PositiveOrZero Integer from,
                                        @RequestParam(required = false, defaultValue = "10")@Positive Integer size) {
        EventParameters parameters = EventParameters.builder()
                .users(users)
                .states(states)
                .categories(categories)
                .rangeStart(rangeStart)
                .rangeEnd(rangeEnd)
                .build();
        log.info("Get events with parameters");
        return eventService.getEventsByParams(parameters, from, size);
    }

    @PutMapping("/{eventId}")
    public EventFullDto updateEventAdmin(@PathVariable @Positive Long eventId,
                                    @RequestBody AdminUpdateEventRequest adminUpdateEventRequest) {
        log.info("Update event with id = {}", eventId);
        return eventService.updateEventAdmin(eventId, adminUpdateEventRequest);
    }

    @PatchMapping("/{eventId}/publish")
    public EventFullDto publishEvent(@PathVariable @Positive Long eventId) {
        log.info("Publish event with id = {}", eventId);
        return eventService.publishEvent(eventId);
    }

    @PatchMapping("/{eventId}/reject")
    public EventFullDto rejectEvent(@PathVariable @Positive Long eventId) {
        log.info("Reject event with id = {}", eventId);
        return eventService.rejectEvent(eventId);
    }
}
