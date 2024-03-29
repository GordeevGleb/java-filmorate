package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.dao.FilmDbStorage;
import ru.yandex.practicum.filmorate.dao.GenreDbStorage;
import ru.yandex.practicum.filmorate.dao.MpaDbStorage;
import ru.yandex.practicum.filmorate.exception.IncorrectGenreException;
import ru.yandex.practicum.filmorate.exception.IncorrectMpaException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmDbStorageTest {

    private final JdbcTemplate jdbcTemplate;

    @Test
    public void testCreateFilm() throws IncorrectMpaException, IncorrectGenreException {
          MpaDbStorage mpaDbStorage = new MpaDbStorage(jdbcTemplate);
          GenreDbStorage genreDbStorage = new GenreDbStorage(jdbcTemplate);
        FilmDbStorage filmDbStorage = new FilmDbStorage(jdbcTemplate, mpaDbStorage, genreDbStorage);
        Film film = Film.builder()
                .id(1L)
                .name("testName")
                .description("testDesc")
                .releaseDate(LocalDate.of(1999, 4, 2))
                .duration(123L)
                .mpa(mpaDbStorage.getMpaById(1).get())
                .genres(genreDbStorage.getAllGenres())
                .build();
filmDbStorage.addFilm(film);
assertThat(filmDbStorage).isNotNull();
assertEquals(1, filmDbStorage.getAllFilms().size());
assertEquals(film, filmDbStorage.getFilmById(film.getId()).get());
    }

@Test
    public void testUpdateFilm() throws IncorrectMpaException, IncorrectGenreException {
        MpaDbStorage mpaDbStorage = new MpaDbStorage(jdbcTemplate);
        GenreDbStorage genreDbStorage = new GenreDbStorage(jdbcTemplate);
        FilmDbStorage filmDbStorage = new FilmDbStorage(jdbcTemplate, mpaDbStorage, genreDbStorage);
        Film film = Film.builder()
                .id(1L)
                .name("testName")
                .description("testDesc")
                .releaseDate(LocalDate.of(1999, 4, 2))
                .duration(123L)
                .mpa(mpaDbStorage.getMpaById(1).get())
                .genres(genreDbStorage.getAllGenres())
                .build();
        filmDbStorage.addFilm(film);
        Film filmToUpdate = Film.builder()
                .id(1L)
                .name("testName1")
                .description("testDesc2")
                .releaseDate(LocalDate.of(1989, 3, 12))
                .duration(223L)
                .mpa(mpaDbStorage.getMpaById(3).get())
                .genres(genreDbStorage.getGenresByFilmId(1L))
                .build();
        filmDbStorage.updateFilm(filmToUpdate);
        assertThat(filmDbStorage.getFilmById(film.getId()).get()).isNotEqualTo(filmDbStorage.getFilmById(film.getId()));
    }
}
