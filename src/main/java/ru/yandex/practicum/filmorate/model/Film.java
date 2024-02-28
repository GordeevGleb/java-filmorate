package ru.yandex.practicum.filmorate.model;

import lombok.*;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.util.MinimumDate;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Component
public class Film {
    private long id = -1;
    @NotNull(message = "Название фильма не существует")
    @NotBlank(message = "Название фильма не может быть пустым")
    private String name;
    @Size(max = 200, message = "Размер описания не должен превышать 200 символов")
    private String description;
    @MinimumDate
    @NotNull(message = "Дата выхода не существует")
    private LocalDate releaseDate;
    @Positive(message = "Длительность фильма не может быть отрицательной")
    private long duration;
    private Set<Long> usersLike = new HashSet<>();

    public Set<Long> addLike(Long userId) {
        usersLike.add(userId);
        return usersLike;
    }

    public Set<Long> deleteLike(Long userId) {
        usersLike.remove(userId);
        return usersLike;
    }
}
