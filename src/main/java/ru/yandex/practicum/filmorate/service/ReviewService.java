package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Review;

import java.util.Collection;

public interface ReviewService {
    Review addReview(Review review);

    Review updateReview(Review review);

    void deleteReview(long id);

    Review getReviewById(long id);

    Collection<Review> getReviews(long filmId, long count);

    void addLike(long reviewId, long userId);

    void addDislike(long reviewId, long userId);

    void deleteLike(long reviewId, long userId);

    void deleteDislike(long reviewId, long userId);
}