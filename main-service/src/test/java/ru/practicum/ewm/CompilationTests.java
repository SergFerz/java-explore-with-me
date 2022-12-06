package ru.practicum.ewm;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.service.CategoryService;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.compilation.service.CompilationService;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.Location;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.event.service.EventService;
import ru.practicum.ewm.statistic.StatisticService;
import ru.practicum.ewm.user.dto.UserDto;
import ru.practicum.ewm.user.service.UserService;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Map;
import java.util.Set;

@Transactional
@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestPropertySource(locations = "classpath:application.properties")
@ExtendWith({MockitoExtension.class})
public class CompilationTests {

    @MockBean
    private StatisticService statisticService;
    private final EntityManager em;
    private final EventService eventService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final CompilationService compilationService;

    @Test
    public void createAndGetCompilation() {
        Mockito.when(statisticService.getEventViewCount((Set<Long>) Mockito.any())).thenReturn(Map.of(1L,1L, 2L,2L));
        CategoryDto categoryDto = new CategoryDto(null, "category1");
        categoryDto = categoryService.createCategory(categoryDto);

        UserDto userDto = new UserDto(null, "user1", "user1@mail.ru");
        userDto = userService.createUser(userDto);

        NewEventDto newEventDto1 = NewEventDto.builder()
                .annotation("annotation1")
                .category(categoryDto.getId())
                .description("description")
                .eventDate("2089-09-14 09:00:00")
                .location(new Location(0, 0))
                .title("title")
                .build();

        EventFullDto eventFullDto1 = eventService.createEvent(userDto.getId(), newEventDto1);

        eventService.publishEvent(eventFullDto1.getId());

        NewEventDto newEventDto2 = NewEventDto.builder()
                .annotation("annotation2")
                .category(categoryDto.getId())
                .description("description")
                .eventDate("2089-09-14 09:00:00")
                .location(new Location(0, 0))
                .title("title")
                .build();

        EventFullDto eventFullDto2 = eventService.createEvent(userDto.getId(), newEventDto2);

        eventService.publishEvent(eventFullDto2.getId());

        NewCompilationDto newCompilationDto = NewCompilationDto.builder()
                .events(Set.of(eventFullDto1.getId(), eventFullDto2.getId()))
                .pinned(true)
                .title("title")
                .build();

        CompilationDto compilationDto = compilationService.create(newCompilationDto);

        TypedQuery<Compilation> query = em.createQuery(
                "Select c from Compilation c " +
                        "where c.title = :title",
                Compilation.class);

        Compilation compilation = query
                .setParameter("title", compilationDto.getTitle())
                .getSingleResult();

        Assertions.assertTrue(compilation != null);
        Assertions.assertEquals(compilation.getId(), compilationDto.getId());
    }
}


