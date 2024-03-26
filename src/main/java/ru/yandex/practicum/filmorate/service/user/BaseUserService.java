package ru.yandex.practicum.filmorate.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;

import java.util.List;

@Service
public class BaseUserService implements UserService {
    @Autowired
    @Qualifier("userStorage")
    private UserStorage userStorage;

    @Override
    public List<Long> addFriend(long userId1, long userId2) throws UserNotFoundException {
        User user1 = userStorage.getUserById(userId1).
                orElseThrow(() -> new UserNotFoundException("Пользователь с id " + userId1 + " не найден"));
        User user2 = userStorage.getUserById(userId2).
                orElseThrow(()-> new UserNotFoundException("Пользователь с id " + userId2 + " не найден"));
        user1.addFriend(userId2);
        user2.addFriend(userId1);
        return new ArrayList<>(user1.getFriends());
    }

    @Override
    public List<Long> deleteFriend(long userId1, long userId2) throws UserNotFoundException {
        User user1 = userStorage.getUserById(userId1).
                orElseThrow(() -> new UserNotFoundException("Пользователь с id " + userId1 + " не найден"));
        User user2 = userStorage.getUserById(userId2).
                orElseThrow(()-> new UserNotFoundException("Пользователь с id " + userId2 + " не найден"));
        user1.removeFriend(userId2);
        user2.removeFriend(userId1);
        return new ArrayList<>(user1.getFriends());
    }

    @Override
    public List<User> getMutualFriends(long userId1, long userId2) throws UserNotFoundException {
        User user1 = userStorage.getUserById(userId1).
                orElseThrow(() -> new UserNotFoundException("Пользователь с id " + userId1 + " не найден"));
        User user2 = userStorage.getUserById(userId2).
                orElseThrow(()-> new UserNotFoundException("Пользователь с id " + userId2 + " не найден"));
        List<User> result = new ArrayList<>();
        for (Long user1Friend : user1.getFriends()) {
            for (Long user2FriendId : user2.getFriends()) {
                if (user2FriendId == user1Friend) {
                    User mutualFriend = userStorage.getUserById(user2FriendId).get();
                    result.add(mutualFriend);
                }
            }
        }
        return result;
    }

    @Override
    public List<User> getUsersFriends(long userId) throws UserNotFoundException {
        User user = userStorage.getUserById(userId).
                orElseThrow(() -> new UserNotFoundException("Пользователь с id " + userId + " не найден"));
        List<User> friends = new ArrayList<>();
        for (Long friendId : user.getFriends()) {
            User friend = userStorage.getUserById(friendId).get();
            friends.add(friend);
        }
        return friends;
    }

    @Override
    public User addUser(User user) {
        return userStorage.addUser(user);
    }

    @Override
    public User updateUser(User user) throws UserNotFoundException {
        userStorage.updateUser(user).
                orElseThrow(() -> new UserNotFoundException("Пользователь " + user.getLogin() + " не найден"));
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    @Override
    public User deleteUser(long userId) {
        return userStorage.deleteUser(userId);
    }

    @Override
    public User getUserById(long userId) throws UserNotFoundException {
        return userStorage.getUserById(userId).
                orElseThrow(()-> new UserNotFoundException("Пользователь с id " + userId + " не найден"));
    }
}
