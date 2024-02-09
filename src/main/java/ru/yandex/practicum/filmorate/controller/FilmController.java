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
        } catch (FilmValidationException e) {
            System.out.println(e.getMessage());
            log.warn("Ошибка валидации фильма {}", film.getName());
        }
        return film;
    }

    @PutMapping("/films")
    public Film updateFilm(@Valid @RequestBody Film film) throws FilmValidationException {
        long filmId = film.getId();
        if (films.containsKey(filmId) && isValid(film)) {
            Film currentFilm = films.get(filmId);
            currentFilm.setName(film.getName());
            currentFilm.setDescription(film.getDescription());
            currentFilm.setReleaseDate(film.getReleaseDate());
            currentFilm.setDuration(film.getDuration());
            log.info("Информация о фильме обновлена");
            return currentFilm;
        } else {
            log.warn("Информация о фильме не была обновлена");
            throw new FilmValidationException("Ошибка при обновлении информации о фильме");
        }
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
            throw new FilmValidationException("Название фильма не может быть пустым");
        }

        if (film.getDescription().length() > MAX_DESCRIPTION_LENGTH) {
            throw new FilmValidationException("Описание фильма не должно превышать 200 символов");
        }

        if (film.getReleaseDate().isBefore(MIN_RELEASE_DATE)) {
            throw new FilmValidationException("Неверная дата релиза фильма {}");
        }

        if (film.getDuration() < MIN_DURATION) {
            throw new FilmValidationException("Длительность фильма не может быть отрицательным значением");
        }
        return true;
    }
}
