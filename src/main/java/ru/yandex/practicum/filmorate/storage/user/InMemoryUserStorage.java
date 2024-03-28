package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
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
    public Optional<User> updateUser(User user) throws UserNotFoundException {
        long userId = user.getId();
            User currentUser = Optional.of(users.get(userId))
                    .orElseThrow(() -> new UserNotFoundException("Пользователь с id " + userId + " не найден"));
            if (currentUser != null) {
                currentUser.setName(user.getName());
                currentUser.setEmail(user.getEmail());
                currentUser.setLogin(user.getLogin());
                currentUser.setBirthday(user.getBirthday());
            }
            return Optional.of(currentUser);
    }

    @Override
    public List<User> getAllUsers() {
        List<User> resultList = new ArrayList<>(users.values());
        return resultList;
    }

    @Override
    public Optional<User> getUserById(long userId) {
       return Optional.of(users.get(userId));
    }

    private long generateId() {
        return ++maxId;
    }
}
