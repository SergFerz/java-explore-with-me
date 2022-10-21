package ru.practicum.ewm.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class NewCompilationDto {

    @NotNull
    private Set<Long> events;

    private Boolean pinned;

    @NotBlank
    private String title;
}
