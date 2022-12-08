package ru.practicum.ewm.event.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.event.controller.EventParameters;
import ru.practicum.ewm.event.controller.EventSort;
import ru.practicum.ewm.event.model.Event;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, EventRepositoryFilterEx {

    List<Event> findEventsByOwnerId(long ownerId, Pageable pageable);

    List<Event> findEx(EventParameters filterParams, EventSort sort, Pageable pageable);

    @Query(value = "SELECT MIN(created) FROM events WHERE id in :eventIds", nativeQuery = true)
    LocalDateTime getMinCreatedDate(Long[] eventIds);
}
