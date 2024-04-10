package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class Review {
    private long reviewId;
    @NotBlank
    private String content;
    private Boolean isPositive;
    private long userId;
    private long filmId;
    private long useful;
}