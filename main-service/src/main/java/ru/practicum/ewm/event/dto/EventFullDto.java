package ru.practicum.ewm.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.practicum.ewm.event.model.EventState;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class EventFullDto extends EventShortDto{

    private String createdOn;
    private String publishedOn;
    private String description;
    private Location location;
    private Integer participantLimit;
    private Boolean requestModeration;
    private EventState state;
}
