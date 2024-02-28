package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.exceptions.UserValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    public List<Long> addFriend(long userId1, long userId2) throws UserValidationException;
    public List<Long> deleteFriend(long userId1, long userId2) throws UserValidationException;
    public List<User> getMutualFriends(long userId1, long userId2) throws UserValidationException;
    public List<User> getUsersFriends(long userId) throws UserValidationException;
}
