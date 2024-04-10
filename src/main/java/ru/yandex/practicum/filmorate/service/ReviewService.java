package ru.yandex.practicum.filmorate.service;

import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.model.Review;

import java.util.Collection;

public interface ReviewService {
    ResponseEntity<?> addReview(Review review);

    ResponseEntity<?> updateReview(Review review);

    ResponseEntity<?> deleteReview(long id);

    Review getReviewById(long id);

    Collection<Review> getReviews(long filmId, long count);

    ResponseEntity<?> addLike(long reviewId, long userId);

    ResponseEntity<?> addDislike(long reviewId, long userId);

    ResponseEntity<?> deleteLike(long reviewId, long userId);

    ResponseEntity<?> deleteDislike(long reviewId, long userId);
}