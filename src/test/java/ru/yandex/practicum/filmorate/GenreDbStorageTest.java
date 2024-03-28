package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.dao.GenreDbStorage;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GenreDbStorageTest {

    private final JdbcTemplate jdbcTemplate;

    @Test
    public void testGenreDb() {
        GenreDbStorage genreDbStorage = new GenreDbStorage(jdbcTemplate);
        List<Genre> allGenres = genreDbStorage.getAllGenres();
        assertThat(allGenres).isNotNull();
        int size = genreDbStorage.getAllGenres().size();
        assertEquals(size, allGenres.size());
        Genre genreFromStorage = genreDbStorage.getGenreById(3).get();
        Genre genreFromList = allGenres.get(2);
        assertEquals(genreFromList, genreFromStorage);
    }
}
