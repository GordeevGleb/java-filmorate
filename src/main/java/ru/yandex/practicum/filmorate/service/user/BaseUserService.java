package ru.yandex.practicum.filmorate.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FriendService;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Service
public class BaseUserService implements UserService {
    @Autowired
    @Qualifier("userStorage")
    private UserStorage userStorage;
    @Autowired
private FriendService friendService;
@Override
    public void addFriend(Long userId1, Long userId2) throws UserNotFoundException {
    User user = userStorage.getUserById(userId1).
            orElseThrow(() ->new UserNotFoundException("Пользователь не найден"));
    User friend = userStorage.getUserById(userId2).
            orElseThrow(() ->new UserNotFoundException("Пользователь не найден"));
    friendService.addFriend(userId1, userId2);
    }

    @Override
    public void deleteFriend(Long userId1, Long userId2) throws UserNotFoundException {
        User user = userStorage.getUserById(userId1).
                orElseThrow(() ->new UserNotFoundException("Пользователь не найден"));
        User friend = userStorage.getUserById(userId2).
                orElseThrow(() ->new UserNotFoundException("Пользователь не найден"));
        friendService.deleteFriend(userId1, userId2);
        userStorage.updateUser(user);
    }

    @Override
    public List<Long> getMutualFriends(Long userId1, Long userId2) {
        return friendService.getMutualFriends(userId1, userId2);
    }

    @Override
    public List<Long> getUsersFriends(Long userId) {
        return friendService.getFriends(userId);
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
