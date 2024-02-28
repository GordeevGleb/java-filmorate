package ru.yandex.practicum.filmorate.storage.film;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.FilmValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
@Component
public interface FilmStorage {
    public Film addFilm(Film film);

    public Film deleteFilm(long filmId);

    public Film updateFilm(Film film) throws FilmValidationException;

    public List<Film> getAllFilms();

    public Film getFilmById(long filmId);
}
