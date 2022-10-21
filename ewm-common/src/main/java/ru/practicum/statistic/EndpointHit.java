package ru.practicum.statistic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.statistic.utils.DateTimeUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class EndpointHit {

    private Long id;

    @NotBlank
    private String app;

    @NotBlank
    private String uri;

    @NotBlank
    private String ip;

    @Pattern(regexp = DateTimeUtils.DATETIME_REGEXP)
    private String timestamp;
}
