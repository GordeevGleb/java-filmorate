package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class Feed {
    @NotBlank
    private long timestamp;
    @NotBlank
    private long userId;
    private EventType eventType;
    private Operation operation;
    private long eventId;
    private long entityId;

    public Feed(long timestamp, long userId, EventType eventType, Operation operation, long entityId) {
        this.timestamp = timestamp;
        this.userId = userId;
        this.eventType = eventType;
        this.operation = operation;
        this.entityId = entityId;
    }
}
