package ch.zhaw.eventsourcing;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class Reservation {
    private final UUID reservationId = UUID.randomUUID();
    private final String customerName;
    private final String showId;
    private final int numberOfTickets;

    public String toString() {
        return "Reservation{" +
                "reservationId=" + reservationId +
                ", customerName='" + customerName + '\'' +
                ", showId='" + showId + '\'' +
                ", numberOfTickets=" + numberOfTickets +
                '}';
    }
}