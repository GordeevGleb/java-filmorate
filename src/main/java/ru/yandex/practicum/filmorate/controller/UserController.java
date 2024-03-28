package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;

import javax.validation.Valid;
import java.util.*;

@RestController
@Slf4j
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public User addUser(@Valid @RequestBody User user) {
        userService.addUser(user);
        return user;
    }

    @PutMapping("/users")
    public User updateUser(@Valid @RequestBody User user) throws UserNotFoundException {
        userService.updateUser(user);
        return user;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/users")
    public User deleteUser(long userId) {
        User userToDelete = userService.deleteUser(userId);
        return userToDelete;
    }

    @GetMapping("users/{userId}")
    public User getUserById(@PathVariable long userId) throws UserNotFoundException {
        return userService.getUserById(userId);
    }

    @PutMapping("/users/{userId}/friends/{friendId}")
    public void addFriend(@PathVariable Long userId,
                                @PathVariable Long friendId) throws UserNotFoundException {
        userService.addFriend(userId, friendId);

    }

    @DeleteMapping("/users/{userId}/friends/{friendId}")
    public void deleteFriend(@PathVariable Long userId,
                                   @PathVariable Long friendId) throws UserNotFoundException {
        userService.deleteFriend(userId, friendId);
    }

    @GetMapping("/users/{userId}/friends")
    public List<Long> getAllUsersFriends(@PathVariable Long userId) throws UserNotFoundException {
        return userService.getUsersFriends(userId);
    }

    @GetMapping("/users/{userId}/friends/common/{friendId}")
    public List<Long> getMutualFriends(@PathVariable Long userId,
                                       @PathVariable Long friendId) throws UserNotFoundException {
        return userService.getMutualFriends(userId, friendId);
    }
}
