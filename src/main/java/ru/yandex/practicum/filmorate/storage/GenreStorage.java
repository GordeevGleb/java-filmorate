package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exception.GenreNotFoundException;
import ru.yandex.practicum.filmorate.exception.MpaNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;
import java.util.Optional;

public interface GenreStorage {
    public Genre addGenre(Genre genre);

    public Genre updateGenre(Genre genre);

    public List<Genre> getAllGenres();

    public Genre deleteGenre(int genreId);

    public Optional<Genre> getGenreById(int genreId);
}
