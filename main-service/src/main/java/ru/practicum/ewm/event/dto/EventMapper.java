package ru.practicum.ewm.event.dto;

import ru.practicum.ewm.category.dto.CategoryMapper;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.EventState;
import ru.practicum.ewm.user.dto.UserMapper;
import ru.practicum.statistic.utils.DateTimeUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class EventMapper {

    public static Event toEvent(NewEventDto newEventDto) {
        boolean paid = false;
        boolean requestModeration = false;
        int participantLimit = 0;
        if (newEventDto.getPaid() != null) {
            paid = newEventDto.getPaid();
        }
        if (newEventDto.getRequestModeration() != null) {
            requestModeration = newEventDto.getRequestModeration();
        }
        if (newEventDto.getParticipantLimit() != null) {
            participantLimit = newEventDto.getParticipantLimit();
        }
        return Event.builder()
                .annotation(newEventDto.getAnnotation())
                .category(new Category(newEventDto.getCategory(), ""))
                .title(newEventDto.getTitle())
                .description(newEventDto.getDescription())
                .created(LocalDateTime.now())
                .eventDate(getDateTimeFromStr(newEventDto.getEventDate(), "eventDate"))
                .locationLat(newEventDto.getLocation().getLat())
                .locationLon(newEventDto.getLocation().getLon())
                .paid(paid)
                .participantLimit(participantLimit)
                .requestModeration(requestModeration)
                .state(EventState.PENDING)
                .build();
    }

    public static void prepareForUpdate(UpdateEventRequest updateEventRequest, Event updateEvent) {
        if (updateEventRequest.getAnnotation() != null) {
            updateEvent.setAnnotation(updateEventRequest.getAnnotation());
        }
        if (updateEventRequest.getCategory() != null) {
            updateEvent.setCategory(new Category(updateEventRequest.getCategory(), ""));
        }
        if (updateEventRequest.getTitle() != null) {
            updateEvent.setTitle(updateEventRequest.getTitle());
        }
        if (updateEventRequest.getDescription() != null) {
            updateEvent.setDescription(updateEventRequest.getDescription());
        }
        if (updateEventRequest.getEventDate() != null) {
            updateEvent.setEventDate(getDateTimeFromStr(updateEventRequest.getEventDate(), "eventDate"));
        }
        if (updateEventRequest.getPaid() != null) {
            updateEvent.setPaid(updateEventRequest.getPaid());
        }
        if (updateEventRequest.getParticipantLimit() != null) {
            updateEvent.setParticipantLimit(updateEventRequest.getParticipantLimit());
        }
    }

    public static void prepareForUpdate(AdminUpdateEventRequest adminUpdateEventRequest,
                                        Event updateEvent) {
        if (adminUpdateEventRequest.getAnnotation() != null) {
            updateEvent.setAnnotation(adminUpdateEventRequest.getAnnotation());
        }
        if (adminUpdateEventRequest.getCategory() != null) {
            updateEvent.setCategory(new Category(adminUpdateEventRequest.getCategory(), ""));
        }
        if (adminUpdateEventRequest.getTitle() != null) {
            updateEvent.setTitle(adminUpdateEventRequest.getTitle());
        }
        if (adminUpdateEventRequest.getDescription() != null) {
            updateEvent.setDescription(adminUpdateEventRequest.getDescription());
        }
        if (adminUpdateEventRequest.getEventDate() != null) {
            updateEvent.setEventDate(getDateTimeFromStr(adminUpdateEventRequest.getEventDate(), "eventDate"));
        }
        if (adminUpdateEventRequest.getPaid() != null) {
            updateEvent.setPaid(adminUpdateEventRequest.getPaid());
        }
        if (adminUpdateEventRequest.getParticipantLimit() != null) {
            updateEvent.setParticipantLimit(adminUpdateEventRequest.getParticipantLimit());
        }
        if (adminUpdateEventRequest.getLocation() != null) {
            updateEvent.setLocationLat(adminUpdateEventRequest.getLocation().getLat());
            updateEvent.setLocationLon(adminUpdateEventRequest.getLocation().getLon());
        }
    }

    public static EventFullDto toEventFullDto(Event event) {
        return EventFullDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(CategoryMapper.toCategoryDto(event.getCategory()))
                .confirmedRequests(0L)
                .createdOn(DateTimeUtils.dateTimeToStr(event.getCreated()))
                .description(event.getDescription())
                .eventDate(DateTimeUtils.dateTimeToStr(event.getEventDate()))
                .initiator(UserMapper.toUserShortDto(event.getOwner()))
                .location(new Location(event.getLocationLat(), event.getLocationLon()))
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(DateTimeUtils.dateTimeToStr(event.getPublished()))
                .requestModeration(event.getRequestModeration())
                .state(event.getState())
                .title(event.getTitle())
                .views(0L)
                .build();
    }

    public static EventShortDto toEventShortDto(Event event) {
        return EventShortDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(CategoryMapper.toCategoryDto(event.getCategory()))
                .confirmedRequests(0L)
                .eventDate(DateTimeUtils.dateTimeToStr(event.getEventDate()))
                .initiator(UserMapper.toUserShortDto(event.getOwner()))
                .paid(event.getPaid())
                .title(event.getTitle())
                .views(0L)
                .build();
    }

    private static LocalDateTime getDateTimeFromStr(String value, String jsonName) {
        try {
            return DateTimeUtils.strToDateTime(value);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(String.format("Ошибка формата даты '%s': %s",
                    jsonName, e.getMessage()));
        }
    }
}
