package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;
import java.util.Optional;

public interface MpaStorage {
    public Mpa addMpa(Mpa mpa);

    public Mpa updateMpa(Mpa mpa);

    public List<Mpa> getAllMpa();

    public Mpa deleteMpa(int mpaId);

    public Optional<Mpa> getMpaById(int mpaId);
}
