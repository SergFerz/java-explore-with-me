package ru.practicum.ewm.subscription.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.event.dto.EventMapper;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.service.EventService;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.subscription.dto.SubscriptionDto;
import ru.practicum.ewm.subscription.dto.SubscriptionMapper;
import ru.practicum.ewm.subscription.model.Subscription;
import ru.practicum.ewm.subscription.repository.SubscriptionRepository;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final EventService eventService;

    @Override
    public SubscriptionDto create(long userId, long friendId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Значение идентификатора " +
                        "пользователя userId в базе не найдено"));
        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new NotFoundException("Значение идентификатора " +
                        "пользователя friendId в базе не найдено"));
        Subscription subscription = Subscription.builder()
                .user(user)
                .friend(friend)
                .build();
        return SubscriptionMapper.toSubscriptionDto(subscriptionRepository.save(subscription));
    }

    @Override
    public List<SubscriptionDto> getSubscriptionList(long userId) {
        return subscriptionRepository.findByUserId(userId).stream()
                .map(SubscriptionMapper::toSubscriptionDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(long subscriptionId) {
        subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new NotFoundException("Указанная подписка не найдена"));
        subscriptionRepository.deleteById(subscriptionId);
    }

    @Override
    public List<EventShortDto> getEventList(long userId) {
        List<EventShortDto> shortDtoList =
                subscriptionRepository.findEventsByUserId(userId).stream()
                        .map(EventMapper::toEventShortDto)
                        .collect(Collectors.toList());
        eventService.enrichDtoList(shortDtoList);
        return shortDtoList;
    }
}