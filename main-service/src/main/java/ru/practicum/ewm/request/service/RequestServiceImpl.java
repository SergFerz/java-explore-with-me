package ru.practicum.ewm.request.service;

import ru.practicum.ewm.request.dto.ParticipationRequestDto;

import java.util.List;

public class RequestServiceImpl implements RequestService{
    @Override
    public List<ParticipationRequestDto> getRequests(Long userId) {
        return null;
    }

    @Override
    public ParticipationRequestDto createRequest(Long userId, Long eventId) {
        return null;
    }

    @Override
    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
        return null;
    }

    @Override
    public List<ParticipationRequestDto> getEventRequests(Long userId, Long eventId) {
        return null;
    }

    @Override
    public ParticipationRequestDto confirmRequest(Long userId, Long requestId, Long reqId) {
        return null;
    }

    @Override
    public ParticipationRequestDto rejectRequest(Long userId, Long requestId, Long reqId) {
        return null;
    }
}
