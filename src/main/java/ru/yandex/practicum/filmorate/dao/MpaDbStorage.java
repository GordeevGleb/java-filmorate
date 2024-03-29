package ru.yandex.practicum.filmorate.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.MpaNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
@Repository
public class MpaDbStorage implements MpaStorage, RowMapper<Mpa> {
    private final JdbcTemplate jdbcTemplate;

@Autowired
    public MpaDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Mpa addMpa(Mpa mpa) {
    String sqlQuery = "insert into MPA(MPA_NAME) values(?)";
    jdbcTemplate.update(sqlQuery, mpa.getName());
        return mpa;
    }

    @Override
    public Mpa updateMpa(Mpa mpa) {
    String sqlQuery = "update MPA set MPA_NAME = ? where MPA_ID = ?";
    jdbcTemplate.update(sqlQuery, mpa.getName(), mpa.getId());
        return mpa;
    }

    @Override
    public List<Mpa> getAllMpa() {
    String sqlQuery = "select MPA_ID, MPA_NAME from MPA";
        return jdbcTemplate.query(sqlQuery, this::mapRow);
    }

    @Override
    public Mpa deleteMpa(int mpaId) {
    String sqlQuery = "delete from MPA where MPA_ID = ?";
    jdbcTemplate.update(sqlQuery, mpaId);
        return null;
    }

    @Override
    public Optional<Mpa> getMpaById(int mpaId) {
    String sqlQuery = "select MPA_ID, MPA_NAME from MPA where MPA_ID = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sqlQuery, this::mapRow, mpaId));
    }

    @Override
    public Mpa mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Mpa.builder()
                .id(rs.getInt(1))
                .name(rs.getString(2))
                .build();
    }
}
