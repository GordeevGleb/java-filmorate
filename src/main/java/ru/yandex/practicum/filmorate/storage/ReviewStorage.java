package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Review;

import java.util.Collection;

public interface ReviewStorage {
    long addAndReturnId(Review review);

    Review update(Review review);

    void deleteReview(long id);

    Review getById(long id);

    Collection<Review> getAll(long count);

    Collection<Review> getFilmReviews(long filmId, long count);

    void addLike(long reviewId, long userId);

    void addDislike(long reviewId, long userId);

    void deleteLikeOrDislike(long reviewId, long userId);

    boolean reviewExists(long id);
}