package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.GenreNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;


import java.util.List;

@RestController
@Slf4j
public class GenreController {
    private final GenreService genreService;
    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }
    @GetMapping("/genres")
    public List<Genre> getAll() {
        return genreService.getAllGenres();
    }
    @GetMapping("/genres/{genreId}")
    public Genre getById(@PathVariable int genreId) throws GenreNotFoundException {
        try {
            return genreService.getGenreById(genreId);
        }
        catch (EmptyResultDataAccessException e) {
            throw new GenreNotFoundException("Жанр с указанным в запросе id не найден");
        }
    }
}
