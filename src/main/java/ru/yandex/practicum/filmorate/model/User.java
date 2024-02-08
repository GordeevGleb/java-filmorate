package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;
@Data
public class User {
    private long id = 0;
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

}
