package ru.yandex.practicum.filmorate.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component("filmStorage")
@Repository
public class FilmDbStorage implements FilmStorage {

//    private final NamedParameterJdbcOperations jdbcOperations;
//@Autowired
//    public FilmDbStorage(NamedParameterJdbcOperations jdbcOperations) {
//        this.jdbcOperations = jdbcOperations;
//    }
    private final JdbcTemplate jdbcTemplate;
@Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Film addFilm(Film film) {
        String sqlQuery = "insert into FILMS(FILM_TITLE, FILM_DESCRIPTION, FILM_RELEASE_DATE," +
                " FILM_DURATION, FILM_MPA, FILM_GENRES) values(?, ?, ?, ?, ?)";
            jdbcTemplate.update(sqlQuery,
                    film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getMpa());
        return film;
    }

    @Override
    public Film deleteFilm(long filmId) {
        String sqlQuery = "delete from FILMS where FILM_ID = ?";
//        jdbcOperations.getJdbcOperations().update(sqlQuery, filmId);
        jdbcTemplate.update(sqlQuery, filmId);
        return null;
    }

    @Override
    public Optional<Film> updateFilm(Film film) {
        String sqlQuery = "update FILMS set FILM_TITLE = ?, FILM_DESCRIPTION = ?, FILM_RELEASE_DATE = ?," +
                "FILM_DURATION = ?, FILM_MPA_ID = ? where FILM_ID = ?";
//        jdbcOperations.getJdbcOperations().update(sqlQuery,
//                film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration()
//        , film.getMpaId(), film.getId());
        jdbcTemplate.update(sqlQuery,
                film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration()
        , film.getMpa(), film.getId());
        return Optional.of(film);
    }

    @Override
    public List<Film> getAllFilms() {
        String sqlQuery = "select FILM_ID, FILM_TITLE, FILM_DESCRIPTION, FILM_RELEASE_DATE, FILM_DURATION," +
                "FILM_MPA_ID from FILMS";
//        return jdbcOperations.query(sqlQuery, this::mapRowToFilm);
        return jdbcTemplate.query(sqlQuery, this::mapRowToFilm);
    }

    @Override
    public Optional<Film> getFilmById(long filmId) {
        String sqlQuery = "select FILM_ID, FILM_TITLE, FILM_DESCRIPTION, FILM_RELEASE_DATE, FILM_DURATION," +
                "FILM_MPA_ID from FILMS where FILM_ID = ?";
//        return jdbcOperations.getJdbcOperations().queryForObject(sqlQuery, this::mapRowToFilm, filmId);
        return Optional.of(jdbcTemplate.queryForObject(sqlQuery, this::mapRowToFilm, filmId));
    }

    private Film mapRowToFilm(ResultSet resultSet, int rowNum) throws SQLException {
        return Film.builder()
                .id(resultSet.getLong(1))
                .name(resultSet.getString(2))
                .description(resultSet.getString(3))
                .releaseDate((resultSet.getDate(4).toLocalDate()))
                .duration(resultSet.getLong(5))
                .mpa(resultSet.getObject(6, Mpa.class))
                .build();
    }
//    private long saveAndReturnId(Film film) {
//        String sqlQuery = "select FILM_ID FROM FILMS WHERE FILM_TITLE = ?";
//        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sqlQuery, film.getName());
//        long filmId = sqlRowSet.getLong(0);
//        film.setId(filmId);
//        return filmId;
//    }
}
