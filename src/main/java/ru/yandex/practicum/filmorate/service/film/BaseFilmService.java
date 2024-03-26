package ru.yandex.practicum.filmorate.service.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import ru.yandex.practicum.filmorate.model.User;
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

    @Override
    public Film addLike(Long filmId, Long userId) throws FilmNotFoundException, UserNotFoundException {
        Film film = filmStorage.getFilmById(filmId)
                .orElseThrow(() -> new FilmNotFoundException("Фильм с id " + filmId + " не найден"));
        User user = userStorage.getUserById(userId)
                        .orElseThrow(() -> new UserNotFoundException("Пользователь с id " + userId + " не найден"));
        film.addLike(userId);
        return film;
    }

    @Override
    public Film deleteLike(Long filmId, Long userId) throws UserNotFoundException, FilmNotFoundException {
        User user = userStorage.getUserById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с id " + userId + " не найден"));
        Film film = filmStorage.getFilmById(filmId)
                .orElseThrow(() -> new FilmNotFoundException("Фильм с id " + filmId + " не найден"));
        film.deleteLike(userId);
        return film;
    }

    @Override
    public List<Film> getTopRatedFilms(Integer count) {
        int defaultCount = 10;
        List<Film> result = new ArrayList<>(filmStorage.getAllFilms());
        result.sort((o1, o2) -> o2.getUsersLike().size() - o1.getUsersLike().size());
        if (count > 0) {
            return result.subList(0, count);
        } else {
            if (result.size() < defaultCount) {
                return result;
            } else {
                return result.subList(0, defaultCount);
            }
        }
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
