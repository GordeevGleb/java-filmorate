package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.exceptions.UserValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    public User addUser(User user);

    public User deleteUser(long userId);

    public User updateUser(User user) throws UserValidationException;

    public List<User> getAllUsers();

    public User getUserById(long userId) throws UserValidationException;
}