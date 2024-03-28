package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface LikeService {
    void addLike(long filmId, long userId);
    void deleteLike(long filmId, long userId);
}
