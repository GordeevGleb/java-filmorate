package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component
public class InMemoryUserStorage implements UserStorage {
    private HashMap<Long, User> users = new HashMap<>();
    private long maxId;

    @Override
    public User addUser(User user) {
        user.setId(generateId());
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User deleteUser(long userId) {
        User userToDelete = users.remove(userId);
        return userToDelete;
    }

    @Override
    public User updateUser(User user) throws UserNotFoundException {
        long userId = user.getId();
        if (users.containsKey(userId)) {
            User currentUser = users.get(userId);
            currentUser.setName(user.getName());
            currentUser.setEmail(user.getEmail());
            currentUser.setLogin(user.getLogin());
            currentUser.setBirthday(user.getBirthday());
            return currentUser;
        } else {
            throw new UserNotFoundException("Пользователь(-и) не найден(-ы)");
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> resultList = new ArrayList<>(users.values());
        return resultList;
    }

    @Override
    public User getUserById(long userId) throws UserNotFoundException {
        if (users.containsKey(userId)) {
            User resultUser = users.get(userId);
            return resultUser;
        } else {
            throw new UserNotFoundException("Пользователь(-и) не найден(-ы)");
        }
    }

    private long generateId() {
        return ++maxId;
    }
}
