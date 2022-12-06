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
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.dto.Location;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.event.service.EventService;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.request.service.RequestService;
import ru.practicum.ewm.statistic.StatisticService;
import ru.practicum.ewm.subscription.dto.SubscriptionDto;
import ru.practicum.ewm.subscription.model.Subscription;
import ru.practicum.ewm.subscription.service.SubscriptionService;
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
public class SubscriptionTests {

    @MockBean
    private StatisticService statisticService;
    private final EntityManager em;
    private final SubscriptionService subscriptionService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final EventService eventService;
    private final RequestService requestService;


    @Test
    public void createSubscription() {

        UserDto userDto = new UserDto(null, "user1", "user1@mail.ru");
        userDto = userService.createUser(userDto);

        UserDto friendDto = new UserDto(null, "user2", "user2@mail.ru");
        friendDto = userService.createUser(friendDto);

        SubscriptionDto subscriptionDto = subscriptionService.create(userDto.getId(),
                friendDto.getId());

        TypedQuery<Subscription> query = em.createQuery(
                "Select s from Subscription s " +
                        "where s.user.id = :userId and s.friend.id = :friendId",
                Subscription.class);

        Subscription subscription = query
                .setParameter("userId", userDto.getId())
                .setParameter("friendId", friendDto.getId())
                .getSingleResult();

        Assertions.assertEquals(subscriptionDto.getId(), subscription.getId());
    }

    @Test
    public void deleteSubscription() {

        UserDto userDto = new UserDto(null, "user1", "user1@mail.ru");
        userDto = userService.createUser(userDto);

        UserDto friendDto = new UserDto(null, "user2", "user2@mail.ru");
        friendDto = userService.createUser(friendDto);

        SubscriptionDto subscriptionDto = subscriptionService.create(userDto.getId(),
                friendDto.getId());

        subscriptionService.deleteById(subscriptionDto.getId());

        TypedQuery<Subscription> query = em.createQuery(
                "Select s from Subscription s " +
                        "where s.id = :id",
                Subscription.class);

        List<Subscription> subscriptionList = query
                .setParameter("id", subscriptionDto.getId())
                .getResultList();

        Assertions.assertTrue(subscriptionList.size() == 0);
    }

    @Test
    public void getSubscriptionList() {

        UserDto userDto = new UserDto(null, "user1", "user1@mail.ru");
        userDto = userService.createUser(userDto);

        UserDto friendDto = new UserDto(null, "user2", "user2@mail.ru");
        friendDto = userService.createUser(friendDto);

        SubscriptionDto subscriptionDto = subscriptionService.create(userDto.getId(),
                friendDto.getId());

        List<SubscriptionDto> list = subscriptionService.getSubscriptionList(userDto.getId());

        Assertions.assertEquals(1, list.size());
        Assertions.assertEquals(subscriptionDto.getId(), list.get(0).getId());
    }

    @Test
    public void getEventList() {

        Mockito.when(statisticService.getEventViewCount((Set<Long>) Mockito.any())).thenReturn(Map.of(1L,1L));
        CategoryDto categoryDto = new CategoryDto(null, "category1");
        categoryDto = categoryService.createCategory(categoryDto);

        UserDto eventOwnerDto = new UserDto(null, "user1", "user1@mail.ru");
        eventOwnerDto = userService.createUser(eventOwnerDto);

        NewEventDto newEventDto = NewEventDto.builder()
                .annotation("Annotation")
                .category(categoryDto.getId())
                .description("description")
                .eventDate("2039-09-14 09:00:00")
                .location(new Location(0, 0))
                .title("title")
                .build();

        EventFullDto eventFullDto = eventService.createEvent(eventOwnerDto.getId(), newEventDto);

        eventService.publishEvent(eventFullDto.getId());

        UserDto friendDto = new UserDto(null, "user2", "user2@mail.ru");
        friendDto = userService.createUser(friendDto);

        ParticipationRequestDto requestDto = requestService.createRequest(friendDto.getId(),
                eventFullDto.getId());

        requestService.confirmRequest(eventOwnerDto.getId(), eventFullDto.getId(),
                requestDto.getId());

        UserDto userDto = new UserDto(null, "user3", "user3@mail.ru");
        userDto = userService.createUser(userDto);

        SubscriptionDto subscriptionDto = subscriptionService.create(userDto.getId(),
                friendDto.getId());

        List<EventShortDto> events = subscriptionService.getEventList(userDto.getId());

        Assertions.assertEquals(1, events.size());
        Assertions.assertEquals(eventFullDto.getId(), events.get(0).getId());
    }
}
