package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Component("filmBean")
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {
    private final NamedParameterJdbcOperations jdbcOperations;
    @Override
    public Film addFilm(Film film) {
        String sqlQuery = "insert into FILMS(FILM_TITLE, FILM_DESCRIPTION, FILM_RELEASE_DATE, FILM_DURATION," +
                "FILM_MPA_ID) values(?, ?, ?, ?, ?)";
         jdbcOperations.getJdbcOperations().update(sqlQuery,
                film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getMpaId());
        return film;
    }

    @Override
    public Film deleteFilm(long filmId) {
        String sqlQuery = "delete from FILMS where FILM_ID = ?";
        jdbcOperations.getJdbcOperations().update(sqlQuery, filmId);
        return null;
    }

    @Override
    public Film updateFilm(Film film) throws FilmNotFoundException {
        String sqlQuery = "update FILMS set FILM_TITLE = ?, FILM_DESCRIPTION = ?, FILM_RELEASE_DATE = ?," +
                "FILM_DURATION = ?, FILM_MPA_ID = ? where FILM_ID = ?";
        jdbcOperations.getJdbcOperations().update(sqlQuery,
                film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration()
        , film.getMpaId(), film.getId());
        return film;
    }

    @Override
    public List<Film> getAllFilms() {
        String sqlQuery = "select FILM_ID, FILM_TITLE, FILM_DESCRIPTION, FILM_RELEASE_DATE, FILM_DURATION," +
                "FILM_MPA_ID from FILMS";
        return jdbcOperations.getJdbcOperations().query(sqlQuery, this::mapRowToEmployee);
    }

    @Override
    public Film getFilmById(long filmId) throws FilmNotFoundException {
        String sqlQuery = "select FILM_ID, FILM_TITLE, FILM_DESCRIPTION, FILM_RELEASE_DATE, FILM_DURATION," +
                "FILM_MPA_ID from FILMS where FILM_ID = ?";
        return jdbcOperations.getJdbcOperations().queryForObject(sqlQuery, this::mapRowToEmployee, filmId);
    }
    private Film mapRowToEmployee(ResultSet resultSet, int rowNum) throws SQLException {
        return Film.builder()
                .id(resultSet.getLong(1))
                .name(resultSet.getString(2))
                .description(resultSet.getString(3))
                .releaseDate((resultSet.getDate(4).toLocalDate()))
                .duration(resultSet.getInt(5))
                .mpaId(resultSet.getInt(6))
                .build();
    }
}
