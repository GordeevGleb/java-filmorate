package ru.yandex.practicum.filmorate.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
import ru.yandex.practicum.filmorate.storage.MpaStorage;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component("filmStorage")
@Repository
public class FilmDbStorage implements FilmStorage, RowMapper<Film> {

    private final JdbcTemplate jdbcTemplate;
    private final MpaStorage mpaStorage;
    private final GenreStorage genreStorage;

@Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate, MpaStorage mpaStorage, GenreStorage genreStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.mpaStorage = mpaStorage;
        this.genreStorage = genreStorage;
    }

    @Override
    public Film addFilm(Film film) {
        String sqlQuery = "insert into FILMS(FILM_TITLE, FILM_DESCRIPTION, FILM_RELEASE_DATE," +
                " FILM_DURATION, FILM_MPA_ID) values(?, ?, ?, ?, ?)";
            jdbcTemplate.update(sqlQuery,
                    film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(),
                    film.getMpa().getId());
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet("select * from FILMS where FILM_TITLE = ?",
                film.getName());
        if (filmRows.next()) {
            film.setId(filmRows.getLong("FILM_ID"));
        }
        return film;
    }

    @Override
    public Film deleteFilm(long filmId) {
        String sqlQuery = "delete from FILMS where FILM_ID = ?";
        jdbcTemplate.update(sqlQuery, filmId);
        return null;
    }

    @Override
    public Optional<Film> updateFilm(Film film) {
        String sqlQuery = "update FILMS set FILM_TITLE = ?, FILM_DESCRIPTION = ?, FILM_RELEASE_DATE = ?," +
                "FILM_DURATION = ?, FILM_MPA_ID = ? where FILM_ID = ?";
        int updateCount = jdbcTemplate.update(sqlQuery,
                film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(),
        film.getMpa().getId(), film.getId());
        if (updateCount != 0) {
            film.setMpa(mpaStorage.getMpaById(film.getMpa().getId()).get());
            return Optional.of(film);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Фильм не найден");
        }
    }

    @Override
    public List<Film> getAllFilms() {
        String sqlQuery = "select FILM_ID, FILM_TITLE, FILM_DESCRIPTION, FILM_RELEASE_DATE, FILM_DURATION," +
                "FILM_MPA_ID from FILMS inner join MPA on FILMS.FILM_MPA_ID = MPA.MPA_ID";
        return jdbcTemplate.query(sqlQuery, this::mapRow);
    }

    @Override
    public Optional<Film> getFilmById(long filmId) {
        String sqlQuery = "select FILM_ID, FILM_TITLE, FILM_DESCRIPTION, FILM_RELEASE_DATE, FILM_DURATION," +
                "FILM_MPA_ID from FILMS where FILM_ID = ?";
        return Optional.of(jdbcTemplate.queryForObject(sqlQuery, this::mapRow, filmId));
    }

    @Override
    public List<Film> getTopRatedFilms(Integer count) {
        String sqlQuery = "select * from FILMS inner join MPA on FILMS.FILM_MPA_ID = MPA.MPA_ID" +
                " join LIKES on FILMS.FILM_ID = LIKES.FILM_ID group by LIKES.USER_ID, FILMS.FILM_ID" +
                " order by count(LIKES.USER_ID) desc limit ?";
        return   jdbcTemplate.query(sqlQuery, this::mapRow, count);
    }

    @Override
    public List<Film> getTopRatedFilms() {
        String sqlQuery = "select * from FILMS inner join MPA on FILMS.FILM_MPA_ID = MPA.MPA_ID" +
                " join LIKES on FILMS.FILM_ID = LIKES.FILM_ID group by LIKES.USER_ID, FILMS.FILM_ID" +
                " order by count(LIKES.USER_ID) desc limit 10";
      return   jdbcTemplate.query(sqlQuery, this::mapRow);
    }

    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
    String genreQuery = "select GENRE_NAME from GENRES join FILM_GENRE on GENRES.GENRE_ID = FILM_GENRE.GENRE_ID" +
            " join FILMS on FILM_GENRE.FILM_ID = FILMS.FILM_ID";
    String likesQuery = "select USER_ID from LIKES join FILMS on LIKES.FILM_ID = FILMS.FILM_ID";
    Film film = Film.builder()
            .id(rs.getLong(1))
            .name(rs.getString(2))
            .description(rs.getString(3))
            .releaseDate(rs.getDate(4).toLocalDate())
            .duration(rs.getLong(5))
            .mpa(mpaStorage.getMpaById(rs.getInt(6)).get())
            .build();
    film.setLikes(jdbcTemplate.queryForList(likesQuery, Long.class));
    film.setGenres(jdbcTemplate.queryForList(genreQuery, Genre.class));
        return film;
    }
}
