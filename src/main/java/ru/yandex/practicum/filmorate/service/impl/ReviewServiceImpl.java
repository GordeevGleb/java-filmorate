package ru.yandex.practicum.filmorate.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Feed;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.model.EventType;
import ru.yandex.practicum.filmorate.model.Operation;
import ru.yandex.practicum.filmorate.service.ReviewService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.ReviewStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.storage.FeedStorage;

import java.util.Collection;
import java.util.Date;

@Slf4j
@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewStorage reviewStorage;
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final FeedStorage feedStorage;

    @Override
    public Review add(Review review) {
        if (!isUserExists(review.getUserId())) {
            log.info("user with id == {} not found", review.getUserId());
            throw new NotFoundException(String.format("user with id == %d not found", review.getUserId()));
        }
        if (!isFilmExists(review.getFilmId())) {
            log.info("film with id == {} not found", review.getFilmId());
            throw new NotFoundException(String.format("film with id == %d not found", review.getFilmId()));
        }
        review.setReviewId(reviewStorage.addAndReturnId(review));
        feedStorage.recordEvent(new Feed(new Date().getTime(), review.getUserId(),
                EventType.REVIEW, Operation.ADD, review.getReviewId()));
        return review;
    }

    @Override
    public Review update(Review review) {
        if (!reviewStorage.isReviewExists(review.getReviewId())) {
            log.info("review with id == {} not found", review.getReviewId());
            throw new NotFoundException(String.format("review with id == %d not found", review.getReviewId()));
        }
        Review oldReview = reviewStorage.getById(review.getReviewId());
        Review newReview = reviewStorage.update(review);
        feedStorage.recordEvent(new Feed(new Date().getTime(), oldReview.getUserId(),
                EventType.REVIEW, Operation.UPDATE, oldReview.getReviewId()));
        return newReview;
    }

    @Override
    public void delete(long id) {
        if (!reviewStorage.isReviewExists(id)) {
            log.info("review with id == {} not found", id);
            throw new NotFoundException(String.format("review with id == %d not found", id));
        }
        Review review = reviewStorage.getById(id);
        feedStorage.recordEvent(new Feed(new Date().getTime(), review.getUserId(),
                EventType.REVIEW, Operation.REMOVE, review.getReviewId()));
        reviewStorage.delete(id);
    }

    @Override
    public Review getById(long id) {
        return reviewStorage.getById(id);
    }

    @Override
    public Collection<Review> getAll(Long filmId, long count) {
        if (filmId == null) {
            return reviewStorage.getAll(count);
        }
        return reviewStorage.getFilmReviews(filmId, count);
    }

    @Override
    public void addLike(long reviewId, long userId) {
        if (!reviewStorage.isReviewExists(reviewId)) {
            log.info("review with id == {} not found", reviewId);
            throw new NotFoundException(String.format("review with id == %d not found", reviewId));
        }
        if (!isUserExists(userId)) {
            log.info("user with id == {} not found", userId);
            throw new NotFoundException(String.format("user with id == %d not found", userId));
        }
        reviewStorage.addLike(reviewId, userId);
    }

    @Override
    public void addDislike(long reviewId, long userId) {
        if (!reviewStorage.isReviewExists(reviewId)) {
            log.info("review with id == {} not found", reviewId);
            throw new NotFoundException(String.format("review with id == %d not found", reviewId));
        }
        if (!isUserExists(userId)) {
            log.info("user with id == {} not found", userId);
            throw new NotFoundException(String.format("user with id == %d not found", userId));
        }
        reviewStorage.addDislike(reviewId, userId);
    }

    @Override
    public void deleteLikeOrDislike(long reviewId, long userId, boolean isDislikeDeleted) {
        if (!reviewStorage.isReviewExists(reviewId)) {
            log.info("review with id == {} not found", reviewId);
            throw new NotFoundException(String.format("review with id == %d not found", reviewId));
        }
        if (!isUserExists(userId)) {
            log.info("user with id == {} not found", userId);
            throw new NotFoundException(String.format("user with id == %d not found", userId));
        }
        reviewStorage.deleteLikeOrDislike(reviewId, userId, isDislikeDeleted);
    }

    private boolean isFilmExists(long id) {
        return filmStorage.findById(id).isPresent();
    }

    private boolean isUserExists(long id) {
        return userStorage.findById(id).isPresent();
    }
}