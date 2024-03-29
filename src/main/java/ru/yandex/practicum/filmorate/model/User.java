package ru.yandex.practicum.filmorate.model;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


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
    public Map<String, Object> toMap() {
        Map<String, Object> values = new HashMap<>();
        values.put("USER_EMAIL", email);
        values.put("USER_LOGIN", login);
        values.put("USER_NAME", name);
        values.put("USER_BIRTHDAY", birthday);
        return values;
    }
}
