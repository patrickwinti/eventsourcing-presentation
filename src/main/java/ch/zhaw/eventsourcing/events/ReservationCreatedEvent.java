package ch.zhaw.eventsourcing.events;

import ch.zhaw.eventsourcing.Reservation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ReservationCreatedEvent extends Event {
    private final Reservation reservation;

    public String toString() {
        return "ReservationCreatedEvent={" +
                "id=" + id +
                ", created=" + created +
                ", reservation=" + reservation +
                "}}";
    }
}
