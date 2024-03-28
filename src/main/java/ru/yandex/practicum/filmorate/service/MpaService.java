package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.exception.MpaNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;
import java.util.Optional;

public interface MpaService {
    public Mpa addMpa(Mpa mpa);

    public Mpa updateMpa(Mpa mpa);

    public List<Mpa> getAllMpa();

    public Mpa deleteMpa(int mpaId);

    public Optional<Mpa> getMpaById(int mpaId) throws MpaNotFoundException;
}