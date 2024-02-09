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
    public User updateUser(@Valid @RequestBody User user) throws UserValidationException {
        long userId = user.getId();
        if (users.containsKey(userId) && isValid(user)) {
            User currentUser = users.get(userId);
            currentUser.setName(user.getName());
            currentUser.setEmail(user.getEmail());
            currentUser.setLogin(user.getLogin());
            currentUser.setBirthday(user.getBirthday());
            log.info("Информация о пользователе обновлена");
            return currentUser;
        } else {
            log.warn("Данные пользователя не были обновлены");
            throw new UserValidationException("Ошибка при обновлении информации о пользователе");
        }
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
                throw new UserValidationException("Некорректный электронный адрес");
            }

            if (user.getLogin().trim().isBlank() || user.getLogin().contains(" ")) {
                throw new UserValidationException("Некорректный логин");
            }

            if (Optional.ofNullable(user.getName()).isEmpty()) {
                log.info("Имя пользователя не указано; в качестве имени использован логин");
                user.setName(user.getLogin());
            }

            if (user.getBirthday().isAfter(LocalDate.now())) {
                throw new UserValidationException("Некорректная дата");
            }
        return true;
    }
}
