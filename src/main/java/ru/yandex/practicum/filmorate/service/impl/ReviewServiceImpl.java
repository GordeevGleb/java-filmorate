package ru.yandex.practicum.filmorate.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ConstraintException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.service.ReviewService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.ReviewStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewStorage reviewStorage;
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Override
    public Review addReview(Review review) {
        userExsistsCheck(review.getUserId());
        filmExsistsCheck(review.getFilmId());
        review.setReviewId(reviewStorage.addAndReturnId(review));
        return review;
    }

    @Override
    public Review updateReview(Review review) {
        reviewExsistsCheck(review.getReviewId());
        return reviewStorage.update(review);
    }

    @Override
    public void deleteReview(long id) {
        reviewExsistsCheck(id);
        reviewStorage.deleteReview(id);
    }

    @Override
    public Review getReviewById(long id) {
        return reviewStorage.getById(id);
    }

    @Override
    public Collection<Review> getReviews(long filmId, long count) {
        Collection<Review> reviews;
        if (count == 0) {
            count = 10;
        }
        if (filmId == 0) {
            return reviewStorage.getAll(count);
        }
        return reviewStorage.getFilmReviews(filmId, count);
    }

    @Override
    public void addLike(long reviewId, long userId) {
        reviewExsistsCheck(reviewId);
        userExsistsCheck(userId);
        reviewStorage.addLike(reviewId, userId);
    }

    @Override
    public void addDislike(long reviewId, long userId) {
        reviewExsistsCheck(reviewId);
        userExsistsCheck(userId);
        reviewStorage.addDislike(reviewId, userId);
    }

    @Override
    public void deleteLike(long reviewId, long userId) {
        reviewExsistsCheck(reviewId);
        userExsistsCheck(userId);
        reviewStorage.deleteLikeOrDislike(reviewId, userId);
    }

    @Override
    public void deleteDislike(long reviewId, long userId) {
        reviewExsistsCheck(reviewId);
        userExsistsCheck(userId);
        reviewStorage.deleteLikeOrDislike(reviewId, userId);
    }

    private void reviewExsistsCheck(long id) {
        if (!reviewStorage.reviewExists(id)) {
            throw new NotFoundException(String.format("Отзыв id=[%s] не найден.", id));
        }
    }

    private void filmExsistsCheck(long id) {
        if (id == 0) {
            throw new ConstraintException("Фильм не может быть null");
        }
        if (filmStorage.findById(id).isEmpty()) {
            throw new NotFoundException(String.format("Фильм id=[%s] не найден.", id));
        }
    }

    private void userExsistsCheck(long id) {
        if (id == 0) {
            throw new ConstraintException("Пользователь не может быть null");
        }
        if (userStorage.findById(id).isEmpty()) {
            throw new NotFoundException(String.format("Пользователь id=[%s] не найден.", id));
        }
    }
}