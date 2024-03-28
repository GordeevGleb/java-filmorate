package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;

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
        String sqlQuery = "insert into FRIENDSHIPS(USER_ID, FRIEND_ID, IS_FRIEND) VALUES(?, ?, ?)";
        jdbcTemplate.update(sqlQuery, userId, friendId, false);
//        if (wasFriendBefore(friendId, userId)) {
//            jdbcTemplate.update("update FRIENDSHIPS" +
//                    " set IS_FRIEND = true where USER_ID = ? AND FRIEND_ID = ?", friendId, userId, true);
//            jdbcTemplate.update("update FRIENDSHIPS" +
//                    " set IS_FRIEND = true where USER_ID = ? AND FRIEND_ID = ?", friendId, userId);
//        }
    }

    @Override
    public List<Long> getFriends(Long userId) {
        String sqlSquery = "select USERS.USER_ID from USERS join FRIENDSHIPS on USERS.USER_ID = FRIENDSHIPS.FRIEND_ID";
        return jdbcTemplate.queryForList(sqlSquery, Long.class);
    }

    @Override
    public void deleteFriend(Long userId, Long friendId) throws UserNotFoundException {
        String sqlQuery = "delete from FRIENDSHIPS where USER_ID = ? AND FRIEND_ID = ?";
        jdbcTemplate.update(sqlQuery, userId, friendId);
//        User user = userService.getUserById(userId);
//        user.getFriends().remove(friendId);
//        if (wasFriendBefore(friendId, userId)) {
//            jdbcTemplate.update("update FRIENDSHIPS set IS_FRIEND = false where USER_ID =? AND FRIEND_ID = ?", friendId);
//        }
    }

    @Override
    public List<Long> getMutualFriends(Long userId1, Long userId2) {
        String sqlQuery = "select * from (select u.USER_ID from USERS as u left join" +
                " FRIENDSHIPS as F on f.FRIEND_ID = u.USER_ID where f.IS_FRIEND = true and u.USER_ID = ?)" +
                " as list1 inner join(select u.USER_ID from USERS as u left join FRIENDSHIPS as f on f.FRIEND_ID = u.USER_ID " +
                "where f.IS_FRIEND = true and u.USER_ID = ?) as list2 on list1.USER_ID = list2.USER_ID";
        return jdbcTemplate.queryForList(sqlQuery, Long.class);
    }

//    @Override
//    public void wasFriendsBefore(Long user, Long friend) {
//        String sqlQuery = "update FRIENDSHIPS set IS_FRIEND = true where USER_ID =? AND FRIEND_ID = ?";
//        jdbcTemplate.update(sqlQuery, user, friend);
//    }
}

