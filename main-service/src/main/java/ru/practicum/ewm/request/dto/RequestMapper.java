package ru.practicum.ewm.request.dto;

import ru.practicum.ewm.request.model.Request;
import ru.practicum.statistic.utils.DateTimeUtils;

public class RequestMapper {

    public static ParticipationRequestDto toParticipationRequestDto(Request request) {
        return ParticipationRequestDto.builder()
                .id(request.getId())
                .created(DateTimeUtils.dateTimeToStr(request.getCreated()))
                .event(request.getEventId())
                .requester(request.getUserId())
                .status(request.getStatus())
                .build();
    }
}
