package ch.zhaw.eventsourcing.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class ReservationCancelledEvent extends Event {
    private final UUID reservationId;

    public String toString() {
        return "\nReservationCancelledEvent={" +
                "\n    id = " + id +
                ",\n    created = " + created +
                ",\n    reservationId = " + reservationId +
                "\n}";
    }
}
