package ru.practicum.ewm.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM users u WHERE u.id IN :ids /*:pageable*/", nativeQuery = true)
    Page<User> findByIds(Long[] ids, Pageable pageable);
}
