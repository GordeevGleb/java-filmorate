package ru.yandex.practicum.filmorate.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
@Repository
public class GenreDbStorage implements GenreStorage, RowMapper<Genre> {
    private final JdbcTemplate jdbcTemplate;

@Autowired
    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Genre addGenre(Genre genre) {
        String sqlQuery = "insert into GENRES(GENRE_NAME) values(?)";
        jdbcTemplate.update(sqlQuery, genre.getName());
        return genre;
    }

    @Override
    public Genre updateGenre(Genre genre) {
        String sqlQuery = "update GENRES set GENRE_NAME = ? where GENRE_ID = ?";
        jdbcTemplate.update(sqlQuery, genre.getName(), genre.getId());
        return genre;
    }

    @Override
    public List<Genre> getAllGenres() {
        String sqlQuery = "select GENRE_ID, GENRE_NAME from GENRES";
        return jdbcTemplate.query(sqlQuery, this::mapRow);
    }

    @Override
    public Genre deleteGenre(int genreId) {
        String sqlQuery = "delete from GENRES where GENRE_ID = ?";
        jdbcTemplate.update(sqlQuery, genreId);
        return null;
    }

    @Override
    public Optional<Genre> getGenreById(int genreId) {
        String sqlQuery = "select GENRE_ID, GENRE_NAME from GENRES where GENRE_ID = ?";
        return Optional.of(jdbcTemplate.queryForObject(sqlQuery, this::mapRow, genreId));
    }

    @Override
    public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Genre.builder()
                .id(rs.getInt(1))
                .name(rs.getString(2))
                .build();
    }
}