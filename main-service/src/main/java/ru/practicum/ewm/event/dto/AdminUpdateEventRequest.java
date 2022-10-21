package ru.practicum.ewm.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Data
@Validated
@AllArgsConstructor
@NoArgsConstructor
public class AdminUpdateEventRequest {

    private String annotation;
    private Location location;
    @Positive
    private Long category;
    private String description;
    private String eventDate;
    private Boolean paid;
    @PositiveOrZero
    private Integer participantLimit;
    private String title;
}
