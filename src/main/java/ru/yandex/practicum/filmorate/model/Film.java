package ru.yandex.practicum.filmorate.model;

import lombok.*;
import ru.yandex.practicum.filmorate.util.MinimumDate;

import javax.validation.constraints.*;
import java.time.LocalDate;
@Data
public class Film {
    private long id = 0;
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
}
