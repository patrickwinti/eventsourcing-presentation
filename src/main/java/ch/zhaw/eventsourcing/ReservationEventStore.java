package ch.zhaw.eventsourcing;

import ch.zhaw.eventsourcing.events.Event;
import ch.zhaw.eventsourcing.events.ReservationCreatedEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservationEventStore {
    private final Map<String, List<Event>> store = new HashMap<>();

    public void addEvent(String showId, Event event) {
        List<Event> events = store.computeIfAbsent(showId, k -> new ArrayList<>());
        events.add(event);
    }

    public List<Event> getAllEvents() {
        return new ArrayList<>(
                store.values().stream().reduce(new ArrayList<>(), (a, b) -> {
                    a.addAll(b);
                    return a;
                }));
    }

    public List<Event> getEventsByShowId(String showId) {
        return store.getOrDefault(showId, new ArrayList<>());
    }

    public String getShowIdByReservationId(String reservationId) {
        return store.entrySet().stream()
                .filter(entry -> entry.getValue().stream()
                        .filter(ReservationCreatedEvent.class::isInstance)
                        .map(ReservationCreatedEvent.class::cast)
                        .anyMatch(event -> event.getReservation().getReservationId().toString().equals(reservationId))
                )
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
    }
}
