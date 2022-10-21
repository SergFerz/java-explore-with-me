package ru.practicum.ewm.compilation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.compilation.model.CompilationEvents;

import java.util.Optional;

public interface CompilationEventsRepository extends JpaRepository<CompilationEvents, Long>  {

    Optional<CompilationEvents> findByCompIdAndEventId(Long compId, Long eventId);
}
