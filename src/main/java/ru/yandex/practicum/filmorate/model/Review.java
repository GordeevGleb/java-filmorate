package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Data
@Builder
public class Review {
    private long reviewId;
    @NotBlank
    private String content;
    private Boolean isPositive;
    private Optional<Long> userId;
    private Optional<Long> filmId;
    private long useful;
}