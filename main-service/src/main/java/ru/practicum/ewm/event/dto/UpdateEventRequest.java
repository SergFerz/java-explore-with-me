package ru.practicum.ewm.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventRequest {

    @Size(max = 1000)
    private String annotation;
    @Positive
    private Long category;
    @NotNull
    @Positive
    private Long eventId;
    @Size(max = 4000)
    private String description;
    private String eventDate;
    private Boolean paid;
    @PositiveOrZero
    private Integer participantLimit;
    @Size(max = 200)
    private String title;
}
