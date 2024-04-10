package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.service.ReviewService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@Validated
@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<?> addReview(@Valid @RequestBody Review review) {
        return reviewService.addReview(review);
    }

    @PutMapping
    public ResponseEntity<?> updateReview(@Valid @RequestBody Review review) {
        return reviewService.updateReview(review);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable long id) {
        return reviewService.deleteReview(id);
    }

    @GetMapping("/{id}")
    public Review getReviewByID(@PathVariable long id) {
        return reviewService.getReviewById(id);
    }

    @GetMapping
    public Collection<Review> getReviews(@RequestParam Optional<Long> filmId, @RequestParam(defaultValue = "10") long count) {
        return reviewService.getReviews(filmId.orElseGet(() -> 0L), count);
    }

    @PutMapping("/{id}/like/{userId}")
    public ResponseEntity<?> addLike(@PathVariable long id, @PathVariable long userId) {
        return reviewService.addLike(id, userId);
    }

    @PutMapping("/{id}/dislike/{userId}")
    public ResponseEntity<?> addDisdislike(@PathVariable long id, @PathVariable long userId) {
        return reviewService.addDislike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public ResponseEntity<?> deleteLike(@PathVariable long id, @PathVariable long userId) {
        return reviewService.deleteLike(id, userId);
    }

    @DeleteMapping("/{id}/dislike/{userId}")
    public ResponseEntity<?> deleteDislike(@PathVariable long id, @PathVariable long userId) {
        return reviewService.deleteDislike(id, userId);
    }
}