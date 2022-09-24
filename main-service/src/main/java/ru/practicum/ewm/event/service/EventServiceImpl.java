package ru.practicum.ewm.event.service;

import ru.practicum.ewm.event.controller.EventParameters;
import ru.practicum.ewm.event.controller.EventSort;
import ru.practicum.ewm.event.dto.*;

import java.util.List;

public class EventServiceImpl implements EventService {
    @Override
    public List<EventFullDto> getEventsByParams(EventParameters parameters, Integer from, Integer size) {
        return null;
    }

    @Override
    public EventFullDto updateEventAdmin(Long eventId, EventUpdateDtoAdmin eventUpdateDtoAdmin) {
        return null;
    }

    @Override
    public EventFullDto updateEventPrivate(Long eventId, EventUpdateDtoPrivate eventUpdateDtoPrivate) {
        return null;
    }

    @Override
    public EventFullDto publishEvent(Long eventId) {
        return null;
    }

    @Override
    public EventFullDto rejectEvent(Long eventId) {
        return null;
    }

    @Override
    public List<EventShortDto> getUserEvents(Long userId, Integer from, Integer size) {
        return null;
    }

    @Override
    public EventFullDto createEvent(Long userId, EventNewDto eventNewDto) {
        return null;
    }

    @Override
    public EventFullDto getUserEventById(Long userId, Long eventId) {
        return null;
    }

    @Override
    public EventFullDto cancelUserEvent(Long userId, Long eventId) {
        return null;
    }

    @Override
    public List<EventShortDto> getEventsPublicByParams(EventParameters parameters, EventSort sort, Integer from, Integer size) {
        return null;
    }

    @Override
    public EventFullDto getEventById(Long eventId) {
        return null;
    }
}
