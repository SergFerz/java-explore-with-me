package ru.practicum.ewm;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.Test;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.service.CategoryService;
import ru.practicum.ewm.event.controller.EventParameters;
import ru.practicum.ewm.event.controller.EventSort;
import ru.practicum.ewm.event.dto.*;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.service.EventService;
import ru.practicum.ewm.statistic.StatisticService;
import ru.practicum.ewm.user.dto.UserDto;
import ru.practicum.ewm.user.service.UserService;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Transactional
@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestPropertySource(locations = "classpath:application.properties")
@ExtendWith({MockitoExtension.class})
public class EventTests {

    @MockBean
    private StatisticService statisticService;
    private final EntityManager em;
    @InjectMocks
    private final EventService eventService;
    private final UserService userService;
    private final CategoryService categoryService;

    @Test
    public void createAndGetEvent() {

        CategoryDto categoryDto = new CategoryDto(null, "category1");
        categoryDto = categoryService.createCategory(categoryDto);

        UserDto userDto = new UserDto(null, "user1", "user1@mail.ru");
        userDto = userService.createUser(userDto);

        NewEventDto newEventDto = NewEventDto.builder()
                .annotation("Annotation")
                .category(categoryDto.getId())
                .description("description")
                .eventDate("2039-09-14 09:00:00")
                .location(new Location(0, 0))
                .title("title")
                .build();

        EventFullDto eventFullDto = eventService.createEvent(userDto.getId(), newEventDto);

        TypedQuery<Event> query = em.createQuery("Select e from Event e where e.annotation = :annotation",
                Event.class);

        Event event = query.setParameter("annotation", newEventDto.getAnnotation()).getSingleResult();

        Assertions.assertTrue(event != null);
        Assertions.assertEquals(event.getId(), eventFullDto.getId());
    }

    @Test
    public void findEvent() {
        Mockito.when(statisticService.getEventViewCount((Set<Long>) Mockito.any())).thenReturn(Map.of(1L,1L, 2L,2L));
        CategoryDto categoryDto = new CategoryDto(null, "category1");
        categoryDto = categoryService.createCategory(categoryDto);

        UserDto userDto = new UserDto(null, "user1", "user1@mail.ru");
        userDto = userService.createUser(userDto);

        NewEventDto newEventDto = NewEventDto.builder()
                .annotation("Экзотическая рыбалка в Сахаре")
                .category(categoryDto.getId())
                .description("description")
                .eventDate("2024-01-01 09:00:00")
                .location(new Location(0, 0))
                .title("title")
                .build();

        EventFullDto eventFullDto = eventService.createEvent(userDto.getId(), newEventDto);

        EventParameters filterParams = EventParameters.builder().text("САХАР").build();

        List<EventShortDto> list = eventService.getEventsPublicByParams(filterParams,
                EventSort.EVENT_DATE, 0, 10);

        Assertions.assertEquals(1, list.size());
    }

    @Test
    public void updateEvent() {

        CategoryDto categoryDto = new CategoryDto(null, "category1");
        categoryDto = categoryService.createCategory(categoryDto);

        UserDto userDto = new UserDto(null, "user1", "user1@mail.ru");
        userDto = userService.createUser(userDto);

        NewEventDto newEventDto = NewEventDto.builder()
                .annotation("Annotation")
                .category(categoryDto.getId())
                .description("description")
                .eventDate("2039-09-14 09:00:00")
                .location(new Location(0, 0))
                .title("title")
                .build();

        EventFullDto eventFullDto = eventService.createEvent(userDto.getId(), newEventDto);

        UpdateEventRequest updateEventRequest = new UpdateEventRequest();
        updateEventRequest.setEventId(eventFullDto.getId());
        updateEventRequest.setAnnotation("change annotation");
        eventService.updateEventPrivate(userDto.getId(), updateEventRequest);

        TypedQuery<Event> query = em.createQuery("Select e from Event e where e.id = :id",
                Event.class);

        Event event = query.setParameter("id", eventFullDto.getId()).getSingleResult();

        Assertions.assertTrue(event != null);
        Assertions.assertEquals(event.getAnnotation(), updateEventRequest.getAnnotation());
    }
}
