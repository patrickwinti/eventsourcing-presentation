package ch.zhaw.eventsourcing.events;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Event {
    public final UUID id = UUID.randomUUID();
    public final LocalDateTime created = LocalDateTime.now();
}