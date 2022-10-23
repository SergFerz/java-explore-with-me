package ru.practicum.ewm.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.statistic.utils.DateTimeUtils;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class NewEventDto {
    @NotBlank
    @Size(max = 1000)
    private String annotation;
    @NotNull
    @Positive
    private Long category;
    @NotBlank
    @Size(max = 4000)
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
    @Size(max = 200)
    private String title;
}
