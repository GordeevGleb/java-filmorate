package ru.yandex.practicum.filmorate.model;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Component
@Builder
public class User {
    private long id = -1;

    @NotNull(message = "Электронная почта не существует")
    @NotBlank(message = "Пустая электронная почта")
    @Email(message = "Некорректная электронная почта")
    private String email;

    @NotNull(message = "Логин не существует")
    @NotBlank(message = "Пустой логин")
    @Pattern(regexp = "\\S+", message = "Логин содержит пробелы")
    private String login;

    private String name;

    @NotNull(message = "Дата рождения не существует")
    @Past(message = "Некорректная дата рождения")
    private LocalDate birthday;
    private Set<Long> friends = new HashSet<>();

    public Set<Long> addFriend(Long friendId) {
        friends.add(friendId);
        return friends;
    }

    public Set<Long> removeFriend(Long friendId) {
        friends.remove(friendId);
        return friends;
    }
}
