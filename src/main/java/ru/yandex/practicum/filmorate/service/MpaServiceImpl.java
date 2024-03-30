package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.MpaNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.dao.MpaStorage;

import java.util.List;
import java.util.Optional;

@Service
public class MpaServiceImpl implements MpaService {

    private MpaStorage mpaStorage;

    @Autowired
    public MpaServiceImpl(MpaStorage mpaStorage) {
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
        try {
            Mpa mpa = mpaStorage.getMpaById(mpaId)
                    .orElseThrow(() -> new MpaNotFoundException("Mpa с id " + mpaId + " не найден"));
            return Optional.ofNullable(mpa);
        } catch (
                EmptyResultDataAccessException e) {
            throw new MpaNotFoundException("Жанр с указанным в запросе id не найден");
        }
    }
}
