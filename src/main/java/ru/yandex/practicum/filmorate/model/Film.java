package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.util.MinimumDate;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Component
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class Film {
    private Long id;
    @NotNull(message = "Название фильма не существует")
    @NotBlank(message = "Название фильма не может быть пустым")
    private String name;
    @Size(max = 200, message = "Размер описания не должен превышать 200 символов")
    private String description;
    @MinimumDate
    @NotNull(message = "Дата выхода не существует")
    private LocalDate releaseDate;
    @Positive(message = "Длительность фильма не может быть отрицательной")
    private Long duration;
    private Mpa mpa;
    private List<Genre> genres = new ArrayList<>();
    @JsonIgnore
    private List<Long> likes = new ArrayList<>();
}
