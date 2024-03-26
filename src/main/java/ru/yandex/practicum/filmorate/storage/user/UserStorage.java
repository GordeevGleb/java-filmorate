package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage {
    public User addUser(User user);

    public User deleteUser(long userId);

    public Optional<User> updateUser(User user) throws UserNotFoundException;

    public List<User> getAllUsers();

    public Optional<User> getUserById(long userId);
}
