package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.UserValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
public class UserController {
    private HashMap<Long, User> users = new HashMap<>();
    private long maxId;

    @PostMapping("/users")
    public User addUser(@Valid @RequestBody User user) {
        try {
            if (isValid(user)) {
                user.setId(generateId());
                users.put(user.getId(), user);
                log.info("Пользователь {} добавлен", user.getLogin());
            }
        } catch (UserValidationException e) {
            log.warn("Ошибка валидации пользователя {}", user.getLogin());
        }
        return user;
    }

    @PutMapping("/users")
    public User updateUser(@Valid @RequestBody User user) {
        long userId = user.getId();
        User currentUser = users.get(userId);
        currentUser.setName(user.getName());
        log.info("Обновлено имя пользователя");
        currentUser.setEmail(user.getEmail());
        log.info("Обновлен электронный адрес пользователя");
        currentUser.setLogin(user.getLogin());
        log.info("Обновлен логин пользователя");
        currentUser.setBirthday(user.getBirthday());
        log.info("Обновлена дата рождения пользователя");
        return currentUser;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        List<User> resultList = new ArrayList<>(users.values());
        log.info("Получен список пользователей");
        return resultList;
    }

    private long generateId() {
        return ++maxId;
    }

    private boolean isValid(User user) throws UserValidationException {
            if (user.getEmail().trim().isBlank() || !user.getEmail().contains("@")) {
                log.warn("Некорректный электронный адрес");
                throw new UserValidationException();
            }

            if (user.getLogin().trim().isBlank() || user.getLogin().contains(" ")) {
                log.warn("Некорректный логин");
                throw new UserValidationException();
            }

            if (Optional.ofNullable(user.getName()).isEmpty()) {
                log.info("Имя пользователя не указано; в качестве имени использован логин");
                user.setName(user.getLogin());
            }

            if (user.getBirthday().isAfter(LocalDate.now())) {
                log.warn("Некорректная дата");
                throw new UserValidationException();
            }
        return true;
    }
}
