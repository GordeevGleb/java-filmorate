package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Review;

import java.util.Collection;
import java.util.Optional;

public interface ReviewService {
    Review add(Review review);

    Review update(Review review);

    void delete(long id);

    Review getById(long id);

    Collection<Review> getAll(Optional<Long> filmId, long count);

    void addLike(long reviewId, long userId);

    void addDislike(long reviewId, long userId);

    void deleteLikeOrDislike(long reviewId, long userId);
}