package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Review;

import java.util.Collection;

public interface ReviewStorage {
    public long addAndReturnId(Review review);

    public Review update(Review review);

    public boolean deleteReview(long id);

    public Review getById(long id);

    public Collection<Review> getAll(long count);

    public Collection<Review> getFilmReviews(long filmId, long count);

    public void addLike(long reviewId, long userId);

    public void addDislike(long reviewId, long userId);

    public boolean reviewExists(long id);
}