package ru.practicum.ewm.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventUpdateDtoPrivate {

    private String annotation;
    @Positive
    private Long category;
    @NotNull
    @Positive
    private Long eventId;
    private String description;
    private String eventDate;
    private Boolean paid;
    @PositiveOrZero
    private Integer participantLimit;
    private String title;
}
