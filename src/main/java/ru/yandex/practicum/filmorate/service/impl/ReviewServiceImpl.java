package ru.yandex.practicum.filmorate.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.service.ReviewService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.ReviewStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import javax.validation.ValidationException;
import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewStorage reviewStorage;
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Override
    public Review add(Review review) {
        if (!isUserExists(review.getUserId())) {
            throw new NotFoundException(String.format("user with id == %d not found", review.getUserId()));
        }
        if (!isFilmExists(review.getFilmId())) {
            throw new NotFoundException(String.format("film with id == %d not found", review.getFilmId()));
        }
        review.setReviewId(reviewStorage.addAndReturnId(review));
        return review;
    }

    @Override
    public Review update(Review review) {
        if (!reviewStorage.isReviewExists(review.getReviewId())) {
            throw new NotFoundException(String.format("review with id == %d not found", review.getReviewId()));
        }
        return reviewStorage.update(review);
    }

    @Override
    public void delete(long id) {
        if (!reviewStorage.isReviewExists(id)) {
            throw new NotFoundException(String.format("review with id == %d not found", id));
        }
        reviewStorage.delete(id);
    }

    @Override
    public Review getById(long id) {
        return reviewStorage.getById(id);
    }

    @Override
    public Collection<Review> getAll(Optional<Long> filmId, long count) {
        if (filmId.isEmpty()) {
            return reviewStorage.getAll(count);
        }
        return reviewStorage.getFilmReviews(filmId.get(), count);
    }

    @Override
    public void addLike(long reviewId, long userId) {
        if (!reviewStorage.isReviewExists(reviewId)) {
            throw new NotFoundException(String.format("review with id == %d not found", reviewId));
        }
        if (!isUserExists(userId)) {
            throw new NotFoundException(String.format("user with id == %d not found", userId));
        }
        reviewStorage.addLike(reviewId, userId);
    }

    @Override
    public void addDislike(long reviewId, long userId) {
        if (!reviewStorage.isReviewExists(reviewId)) {
            throw new NotFoundException(String.format("review with id == %d not found", reviewId));
        }
        if (!isUserExists(userId)) {
            throw new NotFoundException(String.format("user with id == %d not found", userId));
        }
        reviewStorage.addDislike(reviewId, userId);
    }

    @Override
    public void deleteLikeOrDislike(long reviewId, long userId) {
        if (!reviewStorage.isReviewExists(reviewId)) {
            throw new NotFoundException(String.format("review with id == %d not found", reviewId));
        }
        if (!isUserExists(userId)) {
            throw new NotFoundException(String.format("user with id == %d not found", userId));
        }
        reviewStorage.deleteLikeOrDislike(reviewId, userId);
    }

    private boolean isFilmExists(long id) {
        return filmStorage.findById(id).isPresent();
    }

    private boolean isUserExists(long id) {
        return userStorage.findById(id).isPresent();
    }
}