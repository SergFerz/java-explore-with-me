package ru.practicum.ewm.event.service;

import ru.practicum.ewm.event.controller.EventParameters;
import ru.practicum.ewm.event.controller.EventSort;
import ru.practicum.ewm.event.dto.*;

import java.util.List;

public interface EventService {
    List<EventFullDto> getEventsByParams(EventParameters parameters, Integer from, Integer size);

    EventFullDto updateEventAdmin(Long eventId, AdminUpdateEventRequest adminUpdateEventRequest);

    EventFullDto updateEventPrivate(Long eventId, UpdateEventRequest updateEventRequest);

    EventFullDto publishEvent(Long eventId);

    EventFullDto rejectEvent(Long eventId);

    List<EventShortDto> getUserEvents(Long userId, Integer from, Integer size);

    EventFullDto createEvent(Long userId, NewEventDto eventNewDto);

    EventFullDto getUserEventById(Long userId, Long eventId);

    EventFullDto cancelUserEvent(Long userId, Long eventId);

    List<EventShortDto> getEventsPublicByParams(EventParameters parameters, EventSort sort, Integer from, Integer size);

    EventFullDto getEventById(Long eventId);

    /**
     * Обогащает список EventShortDto, EventFullDto числом просмотров из статистики
     * и числом подтвержденных запросов
     * @param listDto - список обогащаемых объектов
     * @param <T> - тип объекта, который будем обогащать (EventShortDto или наследник)
     */
    <T extends EventShortDto> void enrichDtoList(List<T> listDto);
}
