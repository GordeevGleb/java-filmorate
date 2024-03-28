package ru.yandex.practicum.filmorate.storage.film;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;


import java.util.List;
import java.util.Optional;

@Component
public interface FilmStorage {
    public Film addFilm(Film film);

    public Film deleteFilm(long filmId);

    public Optional<Film> updateFilm(Film film) throws FilmNotFoundException;

    public List<Film> getAllFilms();

    public Optional<Film> getFilmById(long filmId) throws FilmNotFoundException;

    public List<Film> getTopRatedFilms(Integer count);

    public List<Film> getTopRatedFilms();
}
