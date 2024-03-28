package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;

import java.util.List;
@Service
public class BaseFriendService implements FriendService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BaseFriendService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addFriend(Long userId, Long friendId) throws UserNotFoundException {
        String sqlQuery = "insert into FRIENDSHIPS(USER_ID, FRIEND_ID) VALUES(?, ?)";
        jdbcTemplate.update(sqlQuery, userId, friendId);
    }

    @Override
    public List<Long> getFriends(Long userId) {
        String sqlSquery = "select FRIEND_ID from FRIENDSHIPS where USER_ID =?";
        return jdbcTemplate.queryForList(sqlSquery, Long.class, userId);
    }

    @Override
    public void deleteFriend(Long userId, Long friendId) {
        String sqlQuery = "delete from FRIENDSHIPS where USER_ID = ? AND FRIEND_ID = ?";
        jdbcTemplate.update(sqlQuery, userId, friendId);
    }

    @Override
    public List<Long> getMutualFriends(Long userId1, Long userId2) {
        String sqlQuery = "select f2.USER_ID as COMMON_FRIEND from FRIENDSHIPS f1 join FRIENDSHIPS f2" +
                " on f1.FRIEND_ID = f2.FRIEND_ID where f1.USER_ID = ? AND f2.USER_ID = ?";
        return jdbcTemplate.queryForList(sqlQuery, Long.class, userId1, userId2);
    }
}

