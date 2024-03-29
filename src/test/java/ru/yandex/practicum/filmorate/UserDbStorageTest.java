package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.dao.UserDbStorage;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserDbStorageTest {

    private final JdbcTemplate jdbcTemplate;

    @Test
    public void testUserDbStorage() throws UserNotFoundException {
        User newUser = new User(1L, "user@email.ru", "vanya123", "Ivan Petrov",
                LocalDate.of(1990, 1, 1));
        UserDbStorage userStorage = new UserDbStorage(jdbcTemplate);
        userStorage.addUser(newUser);
        User savedUser = userStorage.getUserById(1).get();
        assertThat(savedUser)
                .isNotNull() //
                .usingRecursiveComparison()
                .isEqualTo(newUser);

        User newUser1 = new User(1L, "user@email.ru", "vanya123", "Ivan Petrov",
                LocalDate.of(1990, 1, 1));
        userStorage.addUser(newUser1);
        List<User> users = userStorage.getAllUsers();

        assertThat(users).isNotNull();
        assertEquals(newUser, userStorage.getUserById(newUser.getId()).get());

        User newUser2 = new User(1L, "user@email.ru", "vanya123", "Ivan Petrov",
                LocalDate.of(1990, 1, 1));
        userStorage.addUser(newUser2);
        userStorage.updateUser(new User(1L, "123@yandex.com", "testLogin", "test Name",
                LocalDate.of(1965, 1, 2)));
        assertNotNull(userStorage.getUserById(newUser.getId()));
        User savedUser1 = userStorage.getUserById(newUser.getId()).get();
        assertEquals(savedUser1.getName(), "test Name");

        User newUser3 = new User(1L, "user@email.ru", "vanya123", "Ivan Petrov",
                LocalDate.of(1990, 1, 1));
        userStorage.addUser(newUser3);
        assertThat(userStorage).isNotNull();
        assertEquals(userStorage.getUserById(1).get(), newUser);

        userStorage.deleteUser(newUser.getId());
        assertEquals(3, userStorage.getAllUsers().size());
    }
}
