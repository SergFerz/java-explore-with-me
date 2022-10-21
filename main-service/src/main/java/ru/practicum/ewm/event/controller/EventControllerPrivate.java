package ru.practicum.ewm.event.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.dto.UpdateEventRequest;
import ru.practicum.ewm.event.service.EventService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/users")
@Validated
@AllArgsConstructor
@Slf4j
public class EventControllerPrivate {

    private final EventService eventService;

    @GetMapping("/{userId}/events")
    public List<EventShortDto> getUserEvents(@PathVariable @Positive Long userId,
                                             @RequestParam(required = false, defaultValue = "0") @PositiveOrZero Integer from,
                                             @RequestParam(required = false, defaultValue = "10") @Positive Integer size) {
        log.info("Get events by user with id = {}", userId);
        return eventService.getUserEvents(userId, from, size);
    }

    @PatchMapping("/{userId}/events")
    public EventFullDto updateEventPrivate(@PathVariable @Positive Long userId,
                                           @RequestBody UpdateEventRequest updateEventRequest) {
        log.info("Update event by user with id = {}", userId);
        return eventService.updateEventPrivate(userId, updateEventRequest);
    }

    @PostMapping("/{userId}/events")
    public EventFullDto createEvent(@PathVariable @Positive Long userId,
                                    @RequestBody @Valid NewEventDto eventNewDto) {
        log.info("Create event by user with id = {}", userId);
        return eventService.createEvent(userId, eventNewDto);
    }

    @GetMapping("/{userId}/events/{eventId}")
    public EventFullDto getUserEventById(@PathVariable @Positive Long userId,
                                         @PathVariable @Positive Long eventId) {
        log.info("Get event with eventId = {} by user with userId = {}", eventId, userId);
        return eventService.getUserEventById(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public EventFullDto cancelUserEvent(@PathVariable @Positive Long userId,
                                        @PathVariable @Positive Long eventId) {
        log.info("Cancel event with eventId = {} by user with userId = {}", eventId, userId);
        return eventService.cancelUserEvent(userId, eventId);
    }
}
