package ru.practicum.ewm;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.user.dto.UserDto;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.service.UserService;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Transactional
@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestPropertySource(locations = "classpath:application.properties")
public class UserTests {

    private final EntityManager em;
    private final UserService userService;

    @Test
    public void createAndGetUser() {

        UserDto userDto = new UserDto(null, "user1", "user1@mail.ru");
        userDto = userService.createUser(userDto);

        TypedQuery<User> query = em.createQuery("Select u from User u where u.email = :email",
                User.class);

        User user = query.setParameter("email", userDto.getEmail()).getSingleResult();

        Assertions.assertTrue(user != null);
        Assertions.assertEquals(user.getId(), userDto.getId());
        Assertions.assertEquals(user.getName(), userDto.getName());
    }
}