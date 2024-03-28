package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Component
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class User {
    private long id;

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
