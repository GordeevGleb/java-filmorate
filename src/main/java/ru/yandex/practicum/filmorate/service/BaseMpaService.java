package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.MpaNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.util.List;
import java.util.Optional;

@Service
public class BaseMpaService implements MpaService {

    private MpaStorage mpaStorage;

    @Autowired
    public BaseMpaService(MpaStorage mpaStorage) {
        this.mpaStorage = mpaStorage;
    }

    @Override
    public Mpa addMpa(Mpa mpa) {
        return mpaStorage.addMpa(mpa);
    }

    @Override
    public Mpa updateMpa(Mpa mpa) {
        return mpaStorage.updateMpa(mpa);
    }

    @Override
    public List<Mpa> getAllMpa() {
        return mpaStorage.getAllMpa();
    }

    @Override
    public Mpa deleteMpa(int mpaId) {
        return mpaStorage.deleteMpa(mpaId);
    }

    @Override
    public Optional<Mpa> getMpaById(int mpaId) throws MpaNotFoundException {
        Mpa mpa = mpaStorage.getMpaById(mpaId).
                orElseThrow(() -> new MpaNotFoundException("Mpa с id " + mpaId + " не найден"));
        return Optional.ofNullable(mpa);
    }
}
