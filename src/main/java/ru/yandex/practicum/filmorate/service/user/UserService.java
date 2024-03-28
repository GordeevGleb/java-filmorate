package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;


public interface UserService {
    void addFriend(Long userId, Long friendId) throws UserNotFoundException;

    void deleteFriend(Long userId, Long friendId) throws UserNotFoundException;

    List<Long> getUsersFriends(Long userId);

    List<Long> getMutualFriends(Long userId1, Long userId2);

    public User addUser(User user);

    public User updateUser(User user) throws UserNotFoundException;

    public List<User> getAllUsers();

    public User deleteUser(long userId);

    public User getUserById(long userId) throws UserNotFoundException;
}
