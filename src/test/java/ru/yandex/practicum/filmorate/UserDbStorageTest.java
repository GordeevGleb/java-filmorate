package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.dao.UserDbStorage;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@JdbcTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserDbStorageTest {
    private final JdbcTemplate jdbcTemplate;

    @Test
    public void testFindUserById() {
        User newUser = new User(1L, "user@email.ru", "vanya123", "Ivan Petrov",
                LocalDate.of(1990, 1, 1), new ArrayList<>());
        UserDbStorage userStorage = new UserDbStorage(jdbcTemplate);
        userStorage.addUser(newUser);
        User savedUser = userStorage.getUserById(1).get();
        assertThat(savedUser)
                .isNotNull() //
                .usingRecursiveComparison()
                .isEqualTo(newUser);
    }
    @Test
    public void testGetAllUsers() {
        User newUser = new User(1L, "user@email.ru", "vanya123", "Ivan Petrov",
                LocalDate.of(1990, 1, 1), new ArrayList<>());
        UserDbStorage userDbStorage = new UserDbStorage(jdbcTemplate);
        userDbStorage.addUser(newUser);
        List<User> users = userDbStorage.getAllUsers();

        assertThat(users).isNotNull();
        assertEquals(newUser, userDbStorage.getUserById(newUser.getId()).get());
    }
    @Test
    public void testUpdateUser() {
        User newUser = new User(1L, "user@email.ru", "vanya123", "Ivan Petrov",
                LocalDate.of(1990, 1, 1), new ArrayList<>());
        UserDbStorage userDbStorage = new UserDbStorage(jdbcTemplate);
        userDbStorage.addUser(newUser);
        userDbStorage.updateUser(new User(1L, "123@yandex.com", "testLogin", "test Name",
                LocalDate.of(1965, 1, 2), new ArrayList<>()));
        assertNotNull(userDbStorage.getUserById(newUser.getId()));
        User savedUser = userDbStorage.getUserById(newUser.getId()).get();
        assertEquals(savedUser.getName(), "test Name");
    }
    @Test
    public void testAddAndDeleteUser() {
        User newUser = new User(1L, "user@email.ru", "vanya123", "Ivan Petrov",
                LocalDate.of(1990, 1, 1), new ArrayList<>());
        UserDbStorage userDbStorage = new UserDbStorage(jdbcTemplate);
        userDbStorage.addUser(newUser);
        assertThat(userDbStorage).isNotNull();
        assertEquals(userDbStorage.getUserById(1).get(), newUser);

        userDbStorage.deleteUser(newUser.getId());
        assertEquals(0, userDbStorage.getAllUsers().size());
    }
}
