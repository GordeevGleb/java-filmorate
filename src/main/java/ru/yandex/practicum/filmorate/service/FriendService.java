package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.exception.UserNotFoundException;

import java.util.List;

public interface FriendService {
    void addFriend(Long userId, Long friendId) throws UserNotFoundException;

    List<Long> getFriends(Long userId);

    void deleteFriend(Long userId, Long friendId) throws UserNotFoundException;

    List<Long> getMutualFriends(Long userId1, Long userId2);
}
