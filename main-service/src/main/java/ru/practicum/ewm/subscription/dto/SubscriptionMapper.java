package ru.practicum.ewm.subscription.dto;

import ru.practicum.ewm.subscription.model.Subscription;
import ru.practicum.ewm.user.dto.UserMapper;

public class SubscriptionMapper {

    public static SubscriptionDto toSubscriptionDto(Subscription subscription) {
        return SubscriptionDto.builder()
                .id(subscription.getId())
                .user(UserMapper.toUserShortDto(subscription.getUser()))
                .friend(UserMapper.toUserShortDto(subscription.getFriend()))
                .build();
    }
}
