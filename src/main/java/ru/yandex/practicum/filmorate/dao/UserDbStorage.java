package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component("userStorage")
@RequiredArgsConstructor
@Repository
public class UserDbStorage implements UserStorage {

    private final NamedParameterJdbcOperations jdbcOperations;
    @Override
    public User addUser(User user) {
            String sqlQuery = "insert into USERS(USER_EMAIL, USER_LOGIN, USER_NAME, USER_BIRTHDAY)" +
                    " values(?, ?, ?, ?)";
            jdbcOperations.getJdbcOperations().update(sqlQuery,
                    user.getEmail(), user.getLogin(), user.getName(), user.getBirthday());
            return user;
    }

    @Override
    public User deleteUser(long userId) {
        String sqlQuery = "delete from USERS where USER_ID = ?";
        jdbcOperations.getJdbcOperations().update(sqlQuery, userId);
        return null;
    }

    @Override
    public Optional<User> updateUser(User user) {
        String sqlQuery = "update USERS set USER_EMAIL = ?, USER_LOGIN = ?, USER_NAME = ?," +
                "USER_BIRTHDAY = ? where USER_ID = ?";
        jdbcOperations.getJdbcOperations().update(sqlQuery,
                user.getEmail(), user.getLogin(), user.getName(), user.getBirthday());
        return Optional.of(user);
    }

    @Override
    public List<User> getAllUsers() {
        String sqlQuery = "select USER_ID, USER_EMAIL, USER_LOGIN, USER_NAME, USER_BIRTHDAY" +
                " from USERS";
        return jdbcOperations.getJdbcOperations().query(sqlQuery, this::mapRowToUser);
    }

    @Override
    public Optional<User> getUserById(long userId) {
        String sqlQuery = "select USER_ID, USER_EMAIL, USER_LOGIN, USER_NAME, USER_BIRTHDAY" +
                " from USERS where USER_ID = ?";
        return Optional.of(jdbcOperations.getJdbcOperations().queryForObject(sqlQuery, this::mapRowToUser, userId));
    }
    private User mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        return User.builder()
                .id(resultSet.getLong(1))
                .email(resultSet.getString(2))
                .login(resultSet.getString(3))
                .name(resultSet.getString(4))
                .birthday(resultSet.getDate(5).toLocalDate())
                .build();
    }
}
