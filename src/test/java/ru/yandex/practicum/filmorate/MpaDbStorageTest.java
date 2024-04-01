package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.dao.MpaDbStorage;
import ru.yandex.practicum.filmorate.model.Mpa;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class MpaDbStorageTest {

    private final JdbcTemplate jdbcTemplate;

    @Test
    public void testMpaDb() {
        MpaDbStorage mpaDbStorage = new MpaDbStorage(jdbcTemplate);
        List<Mpa> allMpa = mpaDbStorage.getAllMpa();
        assertThat(allMpa).isNotNull();
        int size = mpaDbStorage.getAllMpa().size();
        assertEquals(size, allMpa.size());
        Mpa mpaFromStorage = mpaDbStorage.getMpaById(3).get();
        Mpa mpaFromList = allMpa.get(2);
        assertEquals(mpaFromList, mpaFromStorage);
    }
}

