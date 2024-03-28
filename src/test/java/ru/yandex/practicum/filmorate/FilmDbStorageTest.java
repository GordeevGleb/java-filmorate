package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.dao.FilmDbStorage;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JdbcTest // указываем, о необходимости подготовить бины для работы с БД
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmDbStorageTest {

    private final JdbcTemplate jdbcTemplate;
    private MpaStorage mpaStorage;
    private GenreStorage genreStorage;

    @Test
    public void testFindFilmById() {
        int mpaId = mpaStorage.getMpaById(2).get().getId();
        Film newFilm = Film.builder().
                id(1L)
                .name("test Film")
                .description("test description")
                .releaseDate(LocalDate.of(1965, 1, 3))
                .duration(123L)
                .build();
        FilmDbStorage userStorage = new FilmDbStorage(jdbcTemplate, mpaStorage, genreStorage);
        userStorage.addFilm(newFilm);

        Film savedFilm = userStorage.getFilmById(1).get();

        assertThat(savedFilm)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(newFilm);
    }
}
