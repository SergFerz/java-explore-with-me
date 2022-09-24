package ru.practicum.ewm.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.practicum.ewm.utils.DateTimeUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@Builder
public class EventNewDto {
    @NotBlank
    private String annotation;
    @NotNull
    @Positive
    private Long category;
    @NotBlank
    private String description;
    @NotNull
    @Pattern(regexp = DateTimeUtils.DATETIME_REGEXP)
    private String eventDate;
    @NotNull
    private Location location;
    private Integer participantLimit;
    private Boolean requestModeration;
    private Boolean paid;
    @NotBlank
    private String title;
}
