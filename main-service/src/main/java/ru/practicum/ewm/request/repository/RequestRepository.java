package ru.practicum.ewm.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.request.model.Request;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByUserId(Long userId);

    List<Request> findByEventId(Long eventId);

    @Query(value = "SELECT COUNT(*) FROM requests " +
            "WHERE status = 'APPROVED' " +
            "AND event_id = :eventId ",
            nativeQuery = true)
    long getConfirmCount(long eventId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE requests SET status = 'REJECTED'" +
            "WHERE status = 'PENDING' AND event_id = :eventId",
            nativeQuery = true)
    void rejectPendingRequests(Long eventId);
}
