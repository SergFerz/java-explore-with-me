package ru.practicum.ewm.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.EventState;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.IncorrectActionException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.request.dto.RequestMapper;
import ru.practicum.ewm.request.model.Request;
import ru.practicum.ewm.request.model.RequestStatus;
import ru.practicum.ewm.request.repository.RequestRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;

    @Override
    public ParticipationRequestDto createRequest(Long userId, Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Указанное событие не найдено"));
        if (event.getOwner().getId().equals(userId)) {
            throw new IncorrectActionException("инициатор события не может добавить запрос " +
                    "на участие в своём событии");
        }
        if (event.getState() != EventState.PUBLISHED) {
            throw new IncorrectActionException("нельзя участвовать в неопубликованном событии");
        }
        checkRequestLimit(event);
        Request newRequest = Request.builder()
                .userId(userId)
                .eventId(eventId)
                .created(LocalDateTime.now())
                .status(RequestStatus.PENDING)
                .build();
        if (!event.getRequestModeration()) {
            newRequest.setStatus(RequestStatus.CONFIRMED);
        }
        return RequestMapper.toParticipationRequestDto(requestRepository.save(newRequest));
    }

    @Override
    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
        Request request = getAndCheckRequest(requestId);
        if (!request.getUserId().equals(userId)) {
            throw new IncorrectActionException("Нельзя отменить чужую заявку");
        }
        request.setStatus(RequestStatus.CANCELED);
        return RequestMapper.toParticipationRequestDto(requestRepository.save(request));
    }

    @Override
    public List<ParticipationRequestDto> getRequests(Long userId) {
        return requestRepository.findByUserId(userId).stream()
                .map(RequestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ParticipationRequestDto> getEventRequests(Long userId, Long eventId) {
        getAndCheckEvent(userId, eventId);
        return requestRepository.findByEventId(eventId).stream()
                .map(RequestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public ParticipationRequestDto confirmRequest(Long userId, Long eventId, Long reqId) {
        Event event = getAndCheckEvent(userId, eventId);
        Request request = getAndCheckRequest(reqId);
        if (!request.getEventId().equals(eventId)) {
            throw new IncorrectActionException("Указанный запрос не соответствует " +
                    "указанному событию");
        }
        checkRequestLimit(event);
        request.setStatus(RequestStatus.CONFIRMED);
        requestRepository.save(request);
        // если при подтверждении данной заявки, лимит заявок для события
        // исчерпан, то все неподтверждённые заявки необходимо отклонить
        if (event.getParticipantLimit() > 0) {
            long reqLimit = event.getParticipantLimit();
            long used = requestRepository.getConfirmCount(eventId);
            if (used >= reqLimit) {
                requestRepository.rejectPendingRequests(eventId);
            }
        }
        return RequestMapper.toParticipationRequestDto(request);
    }

    @Override
    public ParticipationRequestDto rejectRequest(Long userId, Long eventId, Long reqId) {
        Event event = getAndCheckEvent(userId, eventId);
        Request request = getAndCheckRequest(reqId);
        if (!request.getEventId().equals(eventId)) {
            throw new IncorrectActionException("Указанный запрос не соответствует " +
                    "указанному событию");
        }
        request.setStatus(RequestStatus.REJECTED);
        requestRepository.save(request);
        return RequestMapper.toParticipationRequestDto(request);
    }

    private Event getAndCheckEvent(Long ownerId, Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Указанное событие не найдено"));
        if (!event.getOwner().getId().equals(ownerId)) {
            throw new IncorrectActionException("Доступ к чужому событию запрещен");
        }
        return event;
    }

    private void checkRequestLimit(Event event) {
        if (event.getParticipantLimit() > 0) {
            if (event.getParticipantLimit() <= requestRepository.getConfirmCount(event.getId())) {
                throw new IncorrectActionException("Достигнут лимит запросов на участие");
            }
        }
    }

    private Request getAndCheckRequest(Long requestId) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Указанная заявка не найдена"));
        return request;
    }
}
