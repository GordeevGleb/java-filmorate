package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.LikeService;
import ru.yandex.practicum.filmorate.service.film.FilmService;
import javax.validation.Valid;
import java.util.*;

@RestController
@Slf4j
public class FilmController {
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping("/films")
    public Film addFilm(@Valid @RequestBody Film film) {
        filmService.addFilm(film);
        return film;
    }

    @PutMapping("/films")
    public Film updateFilm(@Valid @RequestBody Film film) throws FilmNotFoundException {
        filmService.updateFilm(film);
        return film;
    }

    @GetMapping("/films")
    public List<Film> getAllFilms() {
        return filmService.getAllFilms();
    }

    @DeleteMapping("/films")
    public Film deleteFilm(long filmId) {
        return filmService.deleteFilm(filmId);
    }

    @GetMapping("/films/{filmId}")
    public Film getFilmById(@PathVariable long filmId) throws FilmNotFoundException {
        return filmService.getFilmById(filmId);
    }

    @PutMapping("/films/{filmId}/like/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void setLike(@PathVariable Long filmId, @PathVariable Long userId) throws UserNotFoundException,
            FilmNotFoundException {
        filmService.addLike(filmId, userId);
    }

    @DeleteMapping("/films/{filmId}/like/{userId}")
    public Film deleteLike(@PathVariable Long filmId, @PathVariable Long userId) throws FilmNotFoundException,
            UserNotFoundException {
        Film film = filmService.getFilmById(filmId);
        filmService.deleteLike(filmId, userId);
        return film;
    }

    @GetMapping("/films/popular")
    public List<Film> getTopRatedFilms(@RequestParam(required = false) Integer count) {
       return filmService.getTopRatedFilms(count);
    }
}

