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
@Slf4j
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewStorage reviewStorage;
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Override
    public ResponseEntity<?> addReview(Review review) {
        log.info("POST \"/reviews\".");
        userExsistsCheck(review.getUserId());
        filmExsistsCheck(review.getFilmId());
        long id = reviewStorage.addAndReturnId(review);
        review.setReviewId(id);
        log.info(String.format("Создан отзыв id=[%s]", review.getReviewId()));
        return new ResponseEntity<>(review, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updateReview(Review review) {
        log.info("PUT \"/reviews\".");
        reviewExsistsCheck(review.getReviewId());
        Review result = reviewStorage.update(review);
        log.info(String.format("Обновлён отзыв id=[%s]", review.getReviewId()));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteReview(long id) {
        log.info("DELETE \"/reviews/{id}\".");
        reviewExsistsCheck(id);
        reviewStorage.deleteReview(id);
        log.info(String.format("Удалён отзыв id=[%s]", id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public Review getReviewById(long id) {
        log.info("POST \"/reviews/{id}.");
        Review review = reviewStorage.getById(id);
        log.info(String.format("Получен отзыв id=[%s]", review.getReviewId()));
        return review;
    }

    @Override
    public Collection<Review> getReviews(long filmId, long count) {
        log.info("GET \"/reviews?filmId={fimId}&count={count}\".");
        Collection<Review> reviews;
        if (count == 0) {
            count = 10;
        }
        if (filmId == 0) {
            reviews = reviewStorage.getAll(count);
            log.info(String.format("Получены отзывы [ %s ]", reviews));
            return reviews;
        }
        reviews = reviewStorage.getFilmReviews(filmId, count);
        log.info(String.format("Получены отзывы [ %s ]", reviews));
        return reviews;
    }

    @Override
    public ResponseEntity<?> addLike(long reviewId, long userId) {
        log.info("PUT \"/reviews/{id}/like/{userId}\".");
        reviewExsistsCheck(reviewId);
        userExsistsCheck(userId);
        reviewStorage.addLike(reviewId, userId);
        log.info(String.format("Добавлен лайк отзыву id=[%s] от пользователя id=[%s]", reviewId, userId));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> addDislike(long reviewId, long userId) {
        log.info("PUT \"/reviews/{id}/dislike/{userId}\".");
        reviewExsistsCheck(reviewId);
        userExsistsCheck(userId);
        reviewStorage.addDislike(reviewId, userId);
        log.info(String.format("Добавлен дизлайк отзыву id=[%s] от пользователя id=[%s]", reviewId, userId));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteLike(long reviewId, long userId) {
        log.info("DELETE \"/reviews/{id}/like/{userId}\".");
        reviewExsistsCheck(reviewId);
        userExsistsCheck(userId);
        reviewStorage.deleteLikeOrDislike(reviewId, userId);
        log.info(String.format("Удалён лайк отзыву id=[%s] от пользователя id=[%s]", reviewId, userId));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteDislike(long reviewId, long userId) {
        log.info("DELETE \"/reviews/{id}/dislike/{userId}\".");
        reviewExsistsCheck(reviewId);
        userExsistsCheck(userId);
        reviewStorage.deleteLikeOrDislike(reviewId, userId);
        log.info(String.format("Удалён дизлайк отзыву id=[%s] от пользователя id=[%s]", reviewId, userId));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void reviewExsistsCheck(long id) {
        if (!reviewStorage.reviewExists(id)) {
            log.error("Не существует review с таким id");
            throw new NotFoundException(String.format("Отзыв id=[%s] не найден.", id));
        }
    }

    private void filmExsistsCheck(long id) {
        if (id == 0) {
            log.error("Фильм не может быть null");
            throw new ConstraintException("Фильм не может быть null");
        }
        if (filmStorage.findById(id).isEmpty()) {
            log.error("Не существует фильма с таким id");
            throw new NotFoundException(String.format("Фильм id=[%s] не найден.", id));
        }
    }

    private void userExsistsCheck(long id) {
        if (id == 0) {
            log.error("Пользователь не может быть null");
            throw new ConstraintException("Пользователь не может быть null");
        }
        if (userStorage.findById(id).isEmpty()) {
            log.error("Не существует пользователя с таким id");
            throw new NotFoundException(String.format("Пользователь id=[%s] не найден.", id));
        }
    }
}