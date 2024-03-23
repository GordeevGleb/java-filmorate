package ru.yandex.practicum.filmorate.dao;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Component("userBean")
public class UserDbStorage implements UserStorage {
    @Override
    public User addUser(User user) {
        return null;
    }

    @Override
    public User deleteUser(long userId) {
        return null;
    }

    @Override
    public User updateUser(User user) throws UserNotFoundException {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public User getUserById(long userId) throws UserNotFoundException {
        return null;
    }
}
