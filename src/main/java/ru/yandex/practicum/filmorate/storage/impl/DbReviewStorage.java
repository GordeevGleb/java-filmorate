package ru.yandex.practicum.filmorate.storage.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.storage.ReviewStorage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

@Repository
@RequiredArgsConstructor
public class DbReviewStorage implements ReviewStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public long addAndReturnId(Review review) {
        String sqlQuery = "insert into review (content, is_positive, user_id, film_id, useful) values (?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, review.getContent());
            ps.setBoolean(2, review.getIsPositive());
            ps.setLong(3, review.getUserId());
            ps.setLong(4, review.getFilmId());
            ps.setLong(5, review.getUseful());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public Review update(Review review) {
        String sqlQuery = "update review set content = ?, is_positive = ? " +
                "where id = ?";
        jdbcTemplate.update(sqlQuery, review.getContent(), review.getIsPositive(), review.getReviewId());
        return getById(review.getReviewId());
    }

    @Override
    public boolean deleteReview(long id) {
        String sqlQuery = "delete from review where id = ?";
        return jdbcTemplate.update(sqlQuery, id) > 0;
    }

    @Override
    public Review getById(long id) {
        String sqlQuery = "select * from review where id = ?";
        return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToReview, id);
    }

    @Override
    public Collection<Review> getAll(long count) {
        String sqlQuery = "select * from review order by useful desc limit ?";
        return jdbcTemplate.query(sqlQuery, this::mapRowToReview, count);
    }

    @Override
    public Collection<Review> getFilmReviews(long filmId, long count) {
        String sqlQuery = "select * from review where film_id = ? order by useful desc limit ?";
        return jdbcTemplate.query(sqlQuery, this::mapRowToReview, filmId, count);
    }

    @Override
    public void addLike(long reviewId, long userId) {
        String sqlQuery = "update review set useful = useful + 1 WHERE id = ? ";
        jdbcTemplate.update(sqlQuery, reviewId);
    }

    @Override
    public void addDislike(long reviewId, long userId) {
        String sqlQuery = "update review set useful = useful - 1 WHERE id = ? ";
        jdbcTemplate.update(sqlQuery, reviewId);
    }

    @Override
    public boolean reviewExists(long id) {
        String sqlQuery = "select count(*) from review where id = ?";
        Integer count = jdbcTemplate.queryForObject(sqlQuery, Integer.class, id);
        return count != null && count > 0;
    }

    private Review mapRowToReview(ResultSet resultSet, Integer rowNum) throws SQLException {
        return Review.builder()
                .reviewId(resultSet.getLong("id"))
                .content(resultSet.getString("content"))
                .isPositive(resultSet.getBoolean("is_positive"))
                .userId(resultSet.getLong("user_id"))
                .filmId(resultSet.getLong("film_id"))
                .useful(resultSet.getLong("useful"))
                .build();
    }
}