package ru.practicum.ewm.event.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.event.controller.EventParameters;
import ru.practicum.ewm.event.controller.EventSort;
import ru.practicum.ewm.event.model.Event;

import java.util.List;

public interface EventRepositoryFilterEx {

    List<Event> findEx(EventParameters eventParameters, EventSort sort, Pageable pageable);
}
