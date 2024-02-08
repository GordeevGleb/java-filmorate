package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.FilmValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
public class FilmController {
    private HashMap<Long, Film> films = new HashMap<>();
    private long maxId;
    private static final LocalDate MIN_RELEASE_DATE = LocalDate.of(1895, Month.DECEMBER, 28);
    private static final int MAX_DESCRIPTION_LENGTH = 200;
    private static final long MIN_DURATION = 0;

    @PostMapping("/films")
    public Film addFilm(@Valid @RequestBody Film film) {
        try {
            if (isValid(film)) {
                film.setId(generateId());
                films.put(film.getId(), film);
                log.info("Фильм {} добавлен", film.getName());
            }
        }
       catch (FilmValidationException e) {
            log.warn("Ошибка валидации фильма {}", film.getName());
       }
        return film;
    }

    @PutMapping("/films")
    public Film updateFilm(@Valid @RequestBody Film film) {
        long filmId = film.getId();
        Film currentFilm = films.get(filmId);
        currentFilm.setName(film.getName());
        log.info("Обновлено название фильма");
        currentFilm.setDescription(film.getDescription());
        log.info("Обновлено описание фильма");
        currentFilm.setReleaseDate(film.getReleaseDate());
        log.info("Обновлена дата выхода фильма");
        currentFilm.setDuration(film.getDuration());
        log.info("Обновлена длительность фильма");
        return currentFilm;
    }

    @GetMapping("/films")
    public List<Film> getAllFilms() {
        List<Film> resultList = new ArrayList<>(films.values());
        log.info("Получен список фильмов");
        return resultList;
    }

    private long generateId() {
        return ++maxId;
    }
    private boolean isValid(Film film) throws FilmValidationException {
        if (film.getName().trim().isBlank()) {
            log.warn("Название фильма не может быть пустым");
            throw new FilmValidationException();
        }

        if (film.getDescription().length() > MAX_DESCRIPTION_LENGTH) {
            log.warn("Описание фильма не должно превышать 200 символов");
            throw new FilmValidationException();
        }

        if (film.getReleaseDate().isBefore(MIN_RELEASE_DATE)) {
            log.warn("Неверная дата релиза фильма {}", film.getReleaseDate());
            throw new FilmValidationException();
        }

        if (film.getDuration() < MIN_DURATION) {
            log.warn("Длительность фильма не может быть отрицательным значением");
            throw new FilmValidationException();
        }
        return true;
    }
}
