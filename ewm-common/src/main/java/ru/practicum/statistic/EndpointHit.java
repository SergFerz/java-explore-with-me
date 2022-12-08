package ru.practicum.statistic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.statistic.utils.DateTimeUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class EndpointHit {

    private Long id;

    @NotBlank
    @Size(max = 100)
    private String app;

    @NotBlank
    @Size(max = 255)
    private String uri;

    @NotBlank
    @Size(max = 50)
    private String ip;

    @Pattern(regexp = DateTimeUtils.DATETIME_REGEXP)
    private String timestamp;
}
