package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.DirectorStorage;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final DirectorStorage directorStorage;

    @Override
    public Film addFilm(Film film) {
        return filmStorage.create(film);
    }

    @Override
    public Film changeFilm(Film film) {
        return filmStorage.update(film)
                .orElseThrow(() -> new NotFoundException(String.format("film with id %d not found", film.getId()))
        );
    }

    @Override
    public List<Film> getFilms() {
        return filmStorage.findAll();
    }

    @Override
    public Film getFilm(Long id) {
        return filmStorage.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("film with id %d not found", id))
        );
    }

    @Override
    public void putLike(Long id, Long userId) {
        userStorage.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("PUT like: user id %d not found", userId)));
        filmStorage.putLike(id, userId);
    }

    @Override
    public void deleteLike(Long id, Long userId) {
        userStorage.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("user with id == %d not found", userId)));
        filmStorage.deleteLike(id, userId);
    }

    @Override
    public List<Film> getPopular(int count) {
        return filmStorage.getPopular(count);
    }

    @Override
    public List<Film> getByDirectorId(Long id, String sortBy) {
        if (directorStorage.findById(id).isEmpty()) {
            throw new NotFoundException(String.format("director with id == %d not found", id));
        }
        return filmStorage.getByDirectorId(id, sortBy);
    }

    @Override
    public void delete(Long id) {
        filmStorage.delete(id);
    }

    @Override
    public List<Film> getRecommendation(Long id) {
        Long userWithSimilarLikes = userStorage.findUserWithSimilarLikes(id);
        if (userWithSimilarLikes == null) {
            return new ArrayList<>();
        }
        return filmStorage.getFilmRecommendation(id, userWithSimilarLikes);
    }

    @Override
    public List<Film> searchFilms(String query, String by) {
        String[] byArray = by.split(",");

        if (byArray.length == 1) {
            if (byArray[0].equalsIgnoreCase("director")) {
                return filmStorage.findByDirectorNameContaining(query);
            } else if (byArray[0].equalsIgnoreCase("title")) {
                return filmStorage.findByTitleContaining(query);
            } else {
                throw new IllegalArgumentException("Invalid 'by' parameter: " + byArray[0]);
            }
        } else if (byArray.length == 2) {
            if (byArray[0].equalsIgnoreCase("director") || byArray[1].equalsIgnoreCase("title")) {
                return filmStorage.findByTitleContainingOrDirectorNameContaining(query, query);
            } else if (byArray[0].equalsIgnoreCase("title") || byArray[1].equalsIgnoreCase("director")) {
                return filmStorage.findByTitleContainingOrDirectorNameContaining(query, query);
            } else {
                throw new IllegalArgumentException("Invalid 'by' parameter: " + by);
            }
        } else {
            throw new IllegalArgumentException("Invalid 'by' parameter: " + by);
        }
    }
}
