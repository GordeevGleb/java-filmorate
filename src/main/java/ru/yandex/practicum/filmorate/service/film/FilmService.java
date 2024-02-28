package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.exceptions.UserValidationException;
import ru.yandex.practicum.filmorate.model.Film;


import java.util.List;

public interface FilmService {
    public Film addLike(Long filmId, Long userId) throws UserValidationException;

    public Film deleteLike(Long filmId, Long userId) throws UserValidationException;

    public List<Film> getTopRatedFilms();
}
