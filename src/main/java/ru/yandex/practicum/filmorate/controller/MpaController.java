package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.MpaNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
public class MpaController {
    private final MpaService mpaService;
@Autowired
    public MpaController(MpaService mpaService) {
        this.mpaService = mpaService;
    }
    @GetMapping("/mpa")
    public List<Mpa> getAll() {
        return mpaService.getAllMpa();
    }
    @GetMapping("/mpa/{mpaId}")
    public Mpa getById(@PathVariable int mpaId) throws MpaNotFoundException {
    try {
        return mpaService.getMpaById(mpaId).get();
    }
    catch (EmptyResultDataAccessException e) {
        throw new MpaNotFoundException("Рейтинг с указанным в запросе id не найден");
    }
    }
}
