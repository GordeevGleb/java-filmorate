package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.service.ReviewService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@Slf4j
@Validated
@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Review addReview(@Valid @RequestBody Review review) {
        log.info("POST /reviews: {}", review.toString());
        var resultReview = reviewService.addReview(review);
        log.info("completion POST /reviews: {}", resultReview);
        return resultReview;
    }

    @PutMapping
    public Review updateReview(@Valid @RequestBody Review review) {
        log.info("PUT /reviews: {}", review.toString());
        var resultReview = reviewService.updateReview(review);
        log.info("completion PUT /reviews: {}", resultReview);
        return resultReview;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReview(@PathVariable long id) {
        log.info("DELETE /reviews: {}", id);
        reviewService.deleteReview(id);
        log.info("completion DELETE /like: success");
    }

    @GetMapping("/{id}")
    public Review getReviewByID(@PathVariable long id) {
        log.info("GET /reviews: {}", id);
        var resultReview = reviewService.getReviewById(id);
        log.info("completion GET /reviews: {}", resultReview);
        return resultReview;
    }

    @GetMapping
    public Collection<Review> getReviews(@RequestParam Optional<Long> filmId,
                                         @RequestParam(defaultValue = "10") long count) {
        log.info("GET /reviews: all");
        var resultReviews = reviewService.getReviews(filmId.orElseGet(() -> 0L), count);
        log.info("completion GET /reviews: size {}", resultReviews.size());
        return resultReviews;
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable long id, @PathVariable long userId) {
        log.info("PUT /reviews/like: {}, {}", id, userId);
        reviewService.addLike(id, userId);
        log.info("completion PUT /reviews/like: success");
    }

    @PutMapping("/{id}/dislike/{userId}")
    public void addDislike(@PathVariable long id, @PathVariable long userId) {
        log.info("PUT /reviews/dislike: {}, {}", id, userId);
        reviewService.addDislike(id, userId);
        log.info("completion PUT /reviews/dislike: success");
    }

    @DeleteMapping("/{id}/like/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLike(@PathVariable long id, @PathVariable long userId) {
        log.info("DELETE /reviews/like: {}, {}", id, userId);
        reviewService.deleteLike(id, userId);
        log.info("completion DELETE /reviews/like: success");
    }

    @DeleteMapping("/{id}/dislike/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDislike(@PathVariable long id, @PathVariable long userId) {
        log.info("DELETE /reviews/dislike: {}, {}", id, userId);
        reviewService.deleteDislike(id, userId);
        log.info("completion DELETE /reviews/dislike: success");
    }
}