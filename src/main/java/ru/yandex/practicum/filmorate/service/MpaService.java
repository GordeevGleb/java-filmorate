package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.exception.MpaNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

public interface MpaService {
    public Mpa addMpa(Mpa mpa);

    public Mpa updateMpa(Mpa mpa);

    public List<Mpa> getAllMpa();

    public Mpa deleteMpa(int mpaId);

    public Mpa getMpaById(int mpaId) throws MpaNotFoundException;
}
