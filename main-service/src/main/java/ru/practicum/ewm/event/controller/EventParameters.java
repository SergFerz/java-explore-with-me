package ru.practicum.ewm.event.controller;

import lombok.Builder;
import lombok.Data;
import ru.practicum.ewm.event.model.EventState;

import java.time.LocalDateTime;


@Data
@Builder
public class EventParameters {
    private Long[] users;
    private EventState[] states;
    private Long[] categories;
    private LocalDateTime rangeStart;
    private LocalDateTime rangeEnd;
    private String text;
    private Boolean paid;
    private Boolean onlyAvailable;
}
