package ru.yandex.practicum.filmorate.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.IncorrectGenreException;
import ru.yandex.practicum.filmorate.exception.IncorrectMpaException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

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
    public Film addFilm(Film film) throws IncorrectMpaException, IncorrectGenreException {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("FILMS")
                .usingGeneratedKeyColumns("FILM_ID");
        if (!mpaStorage.getAllMpa().contains(film.getMpa())) {
            throw new IncorrectMpaException("Рейтинг, указанный в фильме, не найден");
        } else {
            int filmMpaId = film.getMpa().getId();
            film.getMpa().setName(mpaStorage.getMpaById(filmMpaId).get().getName());
        }
        long filmId = simpleJdbcInsert.executeAndReturnKey(film.toMap()).longValue();
        film.setId(filmId);
       List<Genre> filmGenres = film.getGenres();
        List<Genre> allGenres = genreStorage.getAllGenres();
        if (filmGenres.size() > 0) {
            for (Genre genre : filmGenres) {
                if (!allGenres.contains(genre)) {
                    throw new IncorrectGenreException("Жанр фильма не найден");
                }
                String sqlQuery = "insert into FILM_GENRE(FILM_ID, GENRE_ID) values(?, ?)";
                jdbcTemplate.update(sqlQuery, film.getId(), genre.getId());
            }
        }
        film.setGenres(genreStorage.getGenresByFilmId(filmId));
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
            throw new FilmNotFoundException("Фильм не найден");
        }
    }

    @Override
    public List<Film> getAllFilms() {
        String sqlQuery = "select * from FILMS";
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
        String sqlQuery = "select FILMS.* from FILMS inner join LIKES on FILMS.FILM_ID = LIKES.FILM_ID " +
                "group by FILMS.FILM_ID order by count(LIKES.FILM_ID) desc limit ?";
        return   jdbcTemplate.query(sqlQuery, this::mapRow, count);
    }


    @Override
    public List<Film> getTopRatedFilms() {
        String sqlQuery = "select FILMS.* from FILMS inner join LIKES on FILMS.FILM_ID = LIKES.FILM_ID " +
                "group by FILMS.FILM_ID order by count(LIKES.FILM_ID) desc limit 10";
        return   jdbcTemplate.query(sqlQuery, this::mapRow);
    }

    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        Film film = Film.builder()
                .id(rs.getLong(1))
                .name(rs.getString(2))
                .description(rs.getString(3))
                .releaseDate(rs.getDate(4).toLocalDate())
                .duration(rs.getLong(5))
                .mpa(mpaStorage.getMpaById(rs.getInt(6)).get())
                .build();
        List<Genre> genres = genreStorage.getGenresByFilmId(film.getId());
        String likesQuery = "select USER_ID from LIKES join FILMS on LIKES.FILM_ID = FILMS.FILM_ID" +
                " where FILMS.FILM_ID = ?";
        film.setLikes(jdbcTemplate.queryForList(likesQuery, Long.class, film.getId()));
        film.setGenres(genres);
        return film;
    }
}
