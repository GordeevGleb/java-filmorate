package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component("userStorage")
@RequiredArgsConstructor
@Repository
public class UserDbStorage implements UserStorage, RowMapper<User> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public User addUser(User user) {
            String sqlQuery = "insert into USERS(USER_EMAIL, USER_LOGIN, USER_NAME, USER_BIRTHDAY)" +
                    " values(?, ?, ?, ?)";
            jdbcTemplate.update(sqlQuery,
                    user.getEmail(), user.getLogin(), user.getName(), user.getBirthday());
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("select * from USERS where USER_LOGIN = ?",
                user.getLogin());
        if (userRows.next()) {
            user.setId(userRows.getLong("USER_ID"));
        }
            return user;
    }

    @Override
    public User deleteUser(long userId) {
        String sqlQuery = "delete from USERS where USER_ID = ?";
        jdbcTemplate.update(sqlQuery, userId);
        return null;
    }

    @Override
    public Optional<User> updateUser(User user) {
        String sqlQuery = "update USERS set USER_EMAIL = ?, USER_LOGIN = ?, USER_NAME = ?," +
                "USER_BIRTHDAY = ? where USER_ID = ?";
        int updateCount = jdbcTemplate.update(sqlQuery,
                user.getEmail(), user.getLogin(), user.getName(), user.getBirthday(), user.getId());
        if (updateCount != 0) {
            return Optional.of(user);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден");
        }
    }

    @Override
    public List<User> getAllUsers() {
        String sqlQuery = "select USERS.USER_ID, USERS.USER_EMAIL, USERS.USER_LOGIN, USERS.USER_NAME," +
                " USERS.USER_BIRTHDAY" +
                " from USERS";
        return jdbcTemplate.query(sqlQuery, this::mapRow);
    }

    @Override
    public Optional<User> getUserById(long userId) {
        String sqlQuery = "select USER_ID, USER_EMAIL, USER_LOGIN, USER_NAME, USER_BIRTHDAY" +
                " from USERS where USER_ID = ?";
        return Optional.of(jdbcTemplate.queryForObject(sqlQuery, this::mapRow, userId));
    }

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = User.builder()
                .id(rs.getLong(1))
                .email(rs.getString(2))
                .login(rs.getString(3))
                .name(rs.getString(4))
                .birthday(rs.getDate(5).toLocalDate())
                .build();
        return user;
    }
}
