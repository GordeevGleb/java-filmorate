package ru.yandex.practicum.filmorate.service.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import ru.yandex.practicum.filmorate.service.LikeService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;


@Service
public class BaseFilmService implements FilmService {
    @Autowired
    @Qualifier("userStorage")
    private UserStorage userStorage;
    @Autowired
    @Qualifier("filmStorage")
    private FilmStorage filmStorage;
    @Autowired
    private LikeService likeService;



    @Override
    public void addLike(Long filmId, Long userId) {
        likeService.addLike(filmId, userId);
    }

    @Override
    public void deleteLike(Long filmId, Long userId) {
        likeService.deleteLike(filmId, userId);
    }
    @Override
    public List<Film> getTopRatedFilms(Integer count) {
        List<Film> resultList;
if (Optional.ofNullable(count).isEmpty()) {
   resultList = filmStorage.getTopRatedFilms();
}
else {
   resultList = filmStorage.getTopRatedFilms(count);
}
return resultList;
    }

    public Film addFilm(Film film) {
        filmStorage.addFilm(film);
        return film;
    }

    public Film updateFilm(Film film) throws FilmNotFoundException {
        filmStorage.updateFilm(film).orElseThrow(() -> new FilmNotFoundException("Фильм с id " + film.getId() + " " +
                "не найден"));
        return film;
    }

    public List<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    public Film deleteFilm(long filmId) {
        return filmStorage.deleteFilm(filmId);
    }

    public Film getFilmById(long filmId) throws FilmNotFoundException {
        return filmStorage.getFilmById(filmId).orElseThrow(
                () -> new FilmNotFoundException("Фильм с id " + filmId + " не найден"));
    }
}
