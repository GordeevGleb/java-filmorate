package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.EventType;
import ru.yandex.practicum.filmorate.model.Feed;
import ru.yandex.practicum.filmorate.model.Operation;

import java.util.List;

public interface FeedStorage {
    void recordEvent(Feed feed);

    List<Feed> getFeed(Long id);

    Long getEventTypeId(String eventType);

    Long getOperationId(String operation);
}
